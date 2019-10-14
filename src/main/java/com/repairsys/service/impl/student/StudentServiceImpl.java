package com.repairsys.service.impl.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Developer;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ExceptionEnum;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.board.BoardDaoImpl;
import com.repairsys.dao.impl.developer.DeveloperDao;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.StudentService;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.net.Postman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/30 12:09
 */
public class StudentServiceImpl implements StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private static String FLAG = "1";
    private static final String flag = "flag";

    @Override
    public Result<Boolean> login(String stuId, String stuPassword, HttpSession session) {
        boolean serverCash = false;
        JSONObject jsonObject = null;
        try {
            jsonObject = Postman.doPost(stuId, stuPassword);
        } catch (IOException e) {
            logger.error("校园网服务器异常，暂时没有收到回复");
            logger.error(ExceptionEnum.SERVER_CRASH.getDesc());
            serverCash = true;
        }
        Result<Boolean> result = new Result<>();

        if (serverCash) {
            Developer bean = DeveloperDao.getInstance().login(stuId, stuPassword);
            if (bean != null) {
                result.setResult(ResultEnum.LOGIN_SUCCESS);
                result.setData(true);
            } else {
                result.setData(false);
                result.setResult(ResultEnum.LOGIN_FAIL);
            }
        } else {
            if (jsonObject == null) {
                result.setData(false);
                result.setResult(ResultEnum.LOGIN_FAIL);
            } else if (FLAG.equals(jsonObject.getString(flag))) {
                result.setData(true);
                result.setResult(ResultEnum.LOGIN_SUCCESS);
            } else {
                result.setData(false);
                result.setResult(ResultEnum.LOGIN_FAIL);
            }
        }

        return result;

    }

    @Override
    public Result getHistoryBoard(int page, int limit) {
        AdminServiceImpl adminService = ServiceFactory.getAdminService();
        return adminService.getHistoryBoard(page, limit);
    }

    @Override
    public Result getBoard() {
        BoardDaoImpl boardDao = (BoardDaoImpl) DaoFactory.getBoardDao();
        List list = boardDao.getBoard();
        Result result = new Result();
        result.setData(list);
        result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        return result;
    }

    /**
     * 学生提交报修单
     *
     * @param stuId     学生学号
     * @param queryCode 查询码
     * @param formMsg   表单详情
     * @param formDate  申请日期
     * @param stuMail   学生的 email
     * @param photoId   学生上传的照片
     * @param room      学生宿舍
     * @param stuName   学生姓名
     * @param stuPhone  学生手机
     * @param appointment 预约点数
     * @param appointDate 预约日期
     * @return
     */
    @Override
    public Result<Boolean> applyForm(String stuId, int queryCode, String formMsg, Timestamp formDate, String stuMail, String photoId, String room, String stuName, String stuPhone, String wType, int appointment, String appointDate) {
        FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
        boolean data = formListDao.apply(stuId, queryCode, formMsg, formDate, stuMail, photoId, room, stuName, stuPhone, wType, appointment,appointDate);
        Result<Boolean> result = new Result();
        if (!data) {
            result.setResult(ResultEnum.SUBMITTED_FAILED);
            return result;
        }
        result.setResult(ResultEnum.SUBMITTED_SUCCESSFULLY);
        result.setData(data);
        return result;
    }

}
