package com.repairsys.service.impl.worker;

import com.repairsys.bean.entity.*;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.FormDao;
import com.repairsys.dao.WorkerDao;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.dao.impl.evaluation.EvaluationDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.WorkerService;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/9/29 15:59
 */
public final class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    private WorkerDao workerDao = DaoFactory.getWorkerDao();
    private FormDao formDao = DaoFactory.getFormDao();
    private WorkerScheule workerScheule = WorkerScheule.getInstance();

    @Override
    public Result<Boolean> login(String wId, String password, HttpSession session) {

        Result<Boolean> result = new Result<>();
        if (!StringUtils.login(wId, password)) {
            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 admin可能为空
        Worker worker = workerDao.login(wId, password);
        if (worker == null) {
            return result.setResult(ResultEnum.LOGIN_FAIL);
        }

        session.setAttribute("worker", worker);

        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    @Override
    public Result getIncompleteFormByFormId(String formId, int wKey) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByFormId(formId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.workerQueryIncompleteFormByFormId(formId, wKey);
        if (list.isEmpty()) {
            return result.setResult(ResultEnum.QUERY_FAILED);
        }
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    @Override
    public Result getCompleteFormByFormId(String formId, int wKey) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByFormId(formId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.workerQueryCompleteFormByFormId(formId, wKey);
        if (list.isEmpty()) {
            list = formDao.workerQueryOldByFormId(formId, wKey);
            if (list.isEmpty()) {
                return result.setResult(ResultEnum.QUERY_FAILED);
            }
            result.setData(list);
            return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        }
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    @Override
    public Result getByStudentId(String stuId) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByStudentId(stuId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.queryByStudentId(stuId);
        //找不到该表单
        if (list.isEmpty()) {
            return result.setResult(ResultEnum.QUERY_FAILED);
        }
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    @Override
    public Result updateQueryCode(int queryCode, int formId) {
        Result result = new Result();
        if (workerDao.updateQueryCode(queryCode, formId)) {
            return result.setResult(ResultEnum.UPDATE_QUERYCODE_SUCCESSFULLY);
        }
        return result.setResult(ResultEnum.UPDATE_QUERYCODE_FAILED);
    }

    @Override
    public Result getEvaluation(int wKey) {
        Result result = new Result();
        String evaluation = workerDao.getEvaluation(wKey);
        result.setData(evaluation);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    /**
     * 实现推荐算法，推荐工人
     *
     * @param workerList     工人表单
     * @param workerTimeList 工人时间表
     * @return 返回排序后的集合
     */
    @Override
    public Result getSortedWorkerList(List<Worker> workerList, List<WTime> workerTimeList) {
        EasyTool.resortListOfWorker(workerTimeList, workerList);
        Result<List<Worker>> res = new Result<>();
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        res.setData(workerList);

        return res;
    }

    public Result getSortedWorkerList() {
        WorkerDaoImpl workerDao = (WorkerDaoImpl) DaoFactory.getWorkerDao();
        List<Worker> workerList = workerDao.getAllWorkerList();
        List<WTime> timeList = WorkerScheule.getInstance().getAllWorkerTimeList();
        return this.getSortedWorkerList(workerList, timeList);
    }

    @Override
    public Result getAllIncompleteFormByStudentName(String stuName, int wKey, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.workerGetAllIncompleteListByStudentName(stuName, wKey, page, limit);
        Page res = new Page();
        if (!StringUtils.getByStudentId(stuName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = formListDao.getAllWorkerIncompleteCountByStudentName(stuName, wKey);
        res.setTotalCount(cnt);

        res.setTotalPage(cnt / limit + (cnt % limit == 0 ? 0 : 1));
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);

        if (list.size() == 0) {
            res.setResult(ResultEnum.QUERY_FAILED);
        }

        res.setTargetPage(page);
        res.setSize(list.size());
        logger.debug("{},{}，{}", list, cnt, res.getTotalPage());
        logger.debug("---------------");
        return res;

    }

    @Override
    public Result getAllCompleteFormByStudentName(String stuName, int wKey, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.workerGetAllCompleteListByStudentName(stuName, wKey, page, limit);
        Page res = new Page();
        if (!StringUtils.getByStudentId(stuName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = formListDao.getAllWorkerCompleteCountByStudentName(stuName, wKey);
        res.setTotalCount(cnt);
        res.setTotalPage(cnt / limit + (cnt % limit == 0 ? 0 : 1));
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);

        if (list.size() == 0) {
            res.setResult(ResultEnum.QUERY_FAILED);
        }
        res.setTargetPage(page);
        res.setSize(list.size());
        logger.debug("{},{}，{}", list, cnt, res.getTotalPage());
        logger.debug("---------------");
        return res;

    }

    @Override
    public Result getIncompleteForm(String wId, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        int wKey = dao.getWorkerKeyById(wId);
        List list = dao.workerIncompleteForm(wId, page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = workerDao.getAllIncompleteCountBywKey(wKey);
        res.setTotalCount(cnt);

        res.setTotalPage(cnt / limit + (cnt % limit == 0 ? 0 : 1));
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        if (list.size() == 0) {
            res.setResult(ResultEnum.QUERY_FAILED);
        }

        res.setTargetPage(page);
        res.setSize(list.size());
        logger.debug("{},{}", list, res.getTotalPage());
        logger.debug("---------------");
        return res;

    }

    @Override
    public Result getCompleteForm(String wId, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        int wKey = dao.getWorkerKeyById(wId);
        List list = dao.workerCompleteForm(wId, page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = workerDao.getAllCompleteCountBywKey(wKey);
        res.setTotalCount(cnt);

        res.setTotalPage(cnt / limit + (cnt % limit == 0 ? 0 : 1));
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        if (list.size() == 0) {
            res.setResult(ResultEnum.QUERY_FAILED);
        }

        res.setTargetPage(page);
        res.setSize(list.size());
        logger.debug("{},{}", list, res.getTotalPage());
        logger.debug("---------------");
        return res;

    }

    /**
     * 查询满足条件的工人要求
     *
     * @param date       预约的那天
     * @param hour       预约的整点小时
     * @param workerType 工人类型
     * @return 返回推荐的工人的表单集合
     * @date 2019/10/17
     * @deprecated 新的需求是，根据工人类型，日期，时间，工作量，（地点任务数量） 排序
     */
    @Override
    public Result<List<Worker>> getSuitableWorkerList(Date date, int hour, String workerType) {
        // System.out.println(date);
        // System.out.println(hour);
        // System.out.println(workerType);
        Result<List<Worker>> ans = new Result<>();

        List<Worker> res = workerScheule.recommendByAppointmemntPlus(date, hour, workerType);
        ans.setData(res);
        if (res != null && !res.isEmpty()) {
            ans.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        } else {
            ans.setResult(ResultEnum.QUERY_FAILED);
        }
        System.out.println(ans);

        return ans;
    }

    /**
     * <code>
     * <p>
     * 优先级：时间>工作量>位置
     * <p>
     * 工人在北苑的任务较多，管理员处理报修单页面 这个报修单是北苑的 则把在北苑工作量多的工人排在前面
     * <p>
     * 报修单：17号9点 北苑
     * <p>
     * 甲：17号9点有空 总任务5个 在北苑的任务有4个
     * 乙：17号9点没空 总任务4个 在北苑的任务4个
     * 丙：17号9点有空 总任务4个 在北苑的任务有3个
     * <p>
     * 丙>甲>乙
     * </code>
     *
     * @param date
     * @param hour
     * @param workerType
     * @param location   根据工人的地点排序
     * @return
     */
    public Result<List<RecommendedWorker>> getSuitableWorkerListPlus(Date date, int hour, String workerType, String location) {

        Result<List<RecommendedWorker>> ans = new Result<>();

        List<RecommendedWorker> res = workerScheule.recommendByAppointmemntPlusPlus(date, hour, workerType, location);
        ans.setData(res);
        if (res != null && !res.isEmpty()) {
            ans.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        } else {
            ans.setResult(ResultEnum.QUERY_FAILED);
        }
        System.out.println(ans);

        return ans;
    }

    @Override
    public Result getDetailEvaluation(int wKey) {
        EvaluationDaoImpl evaluationDao = (EvaluationDaoImpl) DaoFactory.getEvaluationDao();
        List<Evaluation> data = evaluationDao.getMsg(wKey);
        Result result = new Result<>();
        result.setData(data);
        if (data.isEmpty()) {
            result.setResult(ResultEnum.GET_EVALUATION_FAILED);
            return result;
        }
        result.setResult(ResultEnum.GET_EVALUATION_SUCCESSFULLY);
        return result;
    }

    public WorkerServiceImpl() {
    }

}
