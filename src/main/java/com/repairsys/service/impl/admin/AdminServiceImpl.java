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
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.AdminService;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
import com.repairsys.util.string.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 18:08
 */
public class AdminServiceImpl implements AdminService {

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
    public Result<Boolean> senMail(String stuMail, int day, int hour) throws Exception {
        Result<Boolean> result = new Result<>();
        if (adminDao.sendMail(stuMail, day, hour)) {
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
     * @param page      当前页面
     * @param limit     设置限制条数
     * @param studentId 学生 id
     * @return 返回学生提交的所有申请状态
     */
    @Override
    public Result<List<Form>> getAllFormByStudentId(int page, int limit, String studentId) {

        WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
        return workerService.getAllFormByStudentId(studentId, page, limit);

    }

    @Override
    public Result getFormListByWorkerName(String wName, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        AdminDaoImpl adminDao = (AdminDaoImpl) DaoFactory.getAdminDao();
        List list = adminDao.queryFormListByWorkerName(wName,page,limit);
        Page res = new Page();
        if (!StringUtils.getByWorkerName(wName)) {
            return res.setResult(ResultEnum.QUERY_EMPTY);
        }
        res.setData(list);
        int cnt = adminDao.getAllCountByWorkerName(wName);
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

    public AdminServiceImpl() {
    }
}
