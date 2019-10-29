package com.repairsys.service.impl.admin;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.AdminDao;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.FormDao;
import com.repairsys.dao.impl.board.BoardDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.AdminService;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 18:08
 */
public final class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminDao adminDao = DaoFactory.getAdminDao();
    private FormDao formDao = DaoFactory.getFormDao();

    @Override
    public Result<Boolean> login(String adminId, String password, HttpSession session) {

        Result<Boolean> result = new Result<>();
        if (!StringUtils.login(adminId, password)) {
            result.setData(false);
            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 admin可能为空
        Admin admin = adminDao.login(adminId, password);
        if (admin == null) {
            result.setData(false);
            return result.setResult(ResultEnum.LOGIN_FAIL);
        }

        session.setAttribute("admin", admin);
        result.setData(true);
        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    @Override
    public Result getIncompleteFormByFormId(String formId) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByFormId(formId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.adminQueryIncompleteFormByFormId(formId);

        if (list.isEmpty()) {
            return result.setResult(ResultEnum.QUERY_FAILED);
        }
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    public Result getCompleteFormByFormId(String formId) {
        Result<List<Form>> result = new Result();
        //查找表单号为空
        if (!StringUtils.getByFormId(formId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.adminQueryCompleteFormByFormId(formId);
        if (list.isEmpty()) {
            list = formDao.adminQueryOldByFormId(formId);
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
    @Deprecated
    public Result getByStudentId(String stuId) {
        Result<List<Form>> result = new Result<List<Form>>();
        //查找表单号为空
        if (!StringUtils.getByStudentId(stuId)) {
            return result.setResult(ResultEnum.QUERY_EMPTY);
        }
        List<Form> list = formDao.queryByStudentId(stuId);
        List<Form> oldList = formDao.queryOldByStudentId(stuId);
        list.addAll(oldList);
        //找不到该表单
        if (list.isEmpty()) {
            return result.setResult(ResultEnum.QUERY_FAILED);
        }
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }

    @Override
    public Result<Boolean> senMail(String stuMail, int day, int hour, String wTel) throws Exception {
        Result<Boolean> result = new Result<>();
        if (stuMail == null || "".equals(stuMail)) {
            result.setData(false);
            return result.setResult(ResultEnum.SEND_MAIL_FAILED);
        }
        if (adminDao.sendMail(stuMail, day, hour, wTel)) {
            result.setData(true);
            return result.setResult(ResultEnum.SEND_MAIL_SUCCESSFULLY);
        }
        result.setData(false);
        return result.setResult(ResultEnum.SEND_MAIL_FAILED);
    }

    /**
     * 模糊查询出工人
     *
     * @param name 工人的名字
     * @return 通过工人的名字模糊查询出结果集，回馈给管理员页面
     * @date 2019/10/3
     */
    @Override
    public Result<List<Worker>> findWorkers(String name) {
        Result<List<Worker>> result = new Result<>();
        List<Worker> list = WorkerDaoImpl.getInstance().fuzzySearchWorkers(name);
        result.setData(list);

        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);

    }

    /**
     * @param page      查询的页面
     * @param limit     一页的记录
     * @param studentId 学生的id号
     * @return 返回分页查询的表单集合
     * @deprecated 没什么用，因为两张表，这只查了一张
     */
    @Deprecated
    @Override
    public Result<List<Form>> getFormByStudentId(int page, int limit, String studentId) {

        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        Page res = new Page<List<Form>>();
        List<Form> forms = formListDao.getPageByStudentId(studentId, page, limit);

        res.setData(forms);

        return res;
    }

    /**
     * @param page    当前页面
     * @param limit   设置限制条数
     * @param stuName 学生姓名
     * @return 返回学生提交的所有申请状态
     */
    @Override
    public Result getAllIncompleteFormByStudentName(String stuName, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.adminGetAllIncompleteListByStudentName(stuName, page, limit);
        Page res = new Page();
        if (!StringUtils.getByStudentId(stuName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = formListDao.getAllAdminIncompleteCountByStudentName(stuName);
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
    public Result getAllCompleteFormByStudentName(String stuName, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.adminGetAllCompleteListByStudentName(stuName, page, limit);
        Page res = new Page();
        if (!StringUtils.getByStudentId(stuName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = formListDao.getAllAdminCompleteCountByStudentName(stuName);
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
    public Result getIncompleteFormByLocation(String location, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = formListDao.adminGetInCompleteListByLocation(location, page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = formListDao.getAllAdminIncompleteCountByLocation(location);
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
    public Result getFormListByWorkerName(String wName, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = dao.batchSearchAllFormByWorkerName(wName, page, limit);
        logger.debug("查询成功1");
        Page res = new Page();
        if (!StringUtils.getByWorkerName(wName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = adminDao.getAllCountByWorkerName(wName);
        logger.debug("查询成功2");
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
    public Result releaseBoard(String board, Timestamp releaseDate) {
        Result result = new Result();
        adminDao.releaseBoard(board, releaseDate);
        result.setResult(ResultEnum.RELEASE_SUCCESSFULLY);
        return result;
    }

    @Override
    public Result getHistoryBoard() {
        BoardDaoImpl boardDao = (BoardDaoImpl) DaoFactory.getBoardDao();
        List list = boardDao.getHistoryBoard();
        Page res = new Page();
        if (list.size() == 0) {
            res.setResult(ResultEnum.QUERY_FAILED);
        } else {
            res.setData(list);
            res.setSize(list.size());
            res.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        }
        return res;
    }

    @Override
    public Result getIncompleteForm(int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = dao.adminIncompleteForm(page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = adminDao.getAllIncompleteCountByAdminKey();
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
    public Result getCompleteForm(int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = dao.adminCompleteForm(page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = adminDao.getAllCompleteCount();
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

    public Result queryByWorkerTypeForm(String wType, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        FormListDaoImpl dao = (FormListDaoImpl) DaoFactory.getFormDao();
        List list = dao.adminQueryWorkerType(wType, page, limit);
        Page res = new Page();
        res.setData(list);
        int cnt = adminDao.getAllCountByWorkerType(wType);
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
    public Result<Boolean> deleteOne(int formId) {
        Result<Boolean> result = new Result<>();
        if (formDao.delete(formId)) {
            result.setData(true);
            return result.setResult(ResultEnum.DELETE_SUCCESSFULLY);
        }
        result.setData(false);
        return result.setResult(ResultEnum.DELETE_FAILED);
    }

    @Override
    public Result<Boolean> arrange(int wKey, String adminId, String appointDate, int appointment, int formId) {
        Result<Boolean> result = new Result<>();
        if (adminDao.queryKey(adminId) == null) {
            return result.setResult(ResultEnum.UPDATE_QUERYCODE_FAILED);
        }
        int adminKey = adminDao.queryKey(adminId).getAdminKey();
        if (formDao.arrange(wKey, adminKey, appointDate, appointment, formId)) {
            result.setData(true);
            return result.setResult(ResultEnum.UPDATE_QUERYCODE_SUCCESSFULLY);
        }
        result.setData(false);
        return result.setResult(ResultEnum.UPDATE_QUERYCODE_FAILED);
    }

    public String getNameById(String adminId) {
        if (adminId == null || "".equals(adminId)) {
            return "";
        }
        String adminName = adminDao.queryName(adminId).getAdminName();
        return adminName;
    }

    public AdminServiceImpl() {
    }
}
