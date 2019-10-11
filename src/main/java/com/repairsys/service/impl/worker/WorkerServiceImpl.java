package com.repairsys.service.impl.worker;

import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.FormDao;
import com.repairsys.dao.WorkerDao;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.dao.impl.worker.WorkerListDaoImpl;
import com.repairsys.service.WorkerService;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/9/29 15:59
 */
public class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    private WorkerDao workerDao = DaoFactory.getWorkerDao();
    private FormDao formDao = DaoFactory.getFormDao();

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
    public Result getByFormId(String formId) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByFormId(formId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.queryByFormId(formId);
        //在未过期表单中找不到时到过期表单中寻找
        if (list.isEmpty()) {
            list = formDao.queryOldByFormId(formId);
            //在过期表单中也找不到
            if (list.isEmpty()) {
                return result.setResult(ResultEnum.QUERY_FAILED);
            }
            //在过期表单中找到了
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

    /**
     * 实现推荐算法，推荐工人
     *
     * @param workerList     工人表单
     * @param workerTimeList 工人时间表
     * @return 返回排序后的集合
     */
    @Override
    public Result getSortedWorkerList(List<Worker> workerList, List<WTime> workerTimeList) {
        EasyTool.resortListOfWorker(workerTimeList,workerList);
        Result<List<Worker>> res = new Result<>();
        res.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        res.setData(workerList);


        return res;
    }


    public Result getSortedWorkerList()
    {
        WorkerDaoImpl workerDao = (WorkerDaoImpl) DaoFactory.getWorkerDao();
        List<Worker> workerList = workerDao.getAllWorkerList();
        List<WTime> timeList = WorkerScheule.getInstance().getAllWorkerTimeList();
        return this.getSortedWorkerList(workerList,timeList);
    }










    public Result getAllFormByStudentId(String stuId, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.getAllListByStudentId(stuId, page, limit);
        Page res = new Page();
        if (!StringUtils.getByStudentId(stuId)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = formListDao.getAllCountByStudentId(stuId);
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
    public Result getIncompleteForm(int wKey, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = dao.workerIncompleteForm(wKey, page, limit);
        Page res = new Page();
        res.setData(list);
//        int cnt = adminDao.getAllCountByWorkerName(wName);
//        res.setTotalCount(cnt);
//
//        res.setTotalPage(cnt / limit + (cnt % limit == 0 ? 0 : 1));
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

    public WorkerServiceImpl() {
    }

}
