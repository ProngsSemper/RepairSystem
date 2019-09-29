package com.repairsys.service.impl.student;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.FormDao;
import com.repairsys.dao.impl.StudentDaoImpl;


import com.repairsys.bean.entity.Student;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.service.StudentService;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Prongs
 */
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private StudentDaoImpl studentDao = (StudentDaoImpl) DaoFactory.getStudentDao();
    // private FormDao formDao = DaoFactory.

    @Override
    public Result<Boolean> login(String stuId, String password, HttpSession session) {



        Result<Boolean> result = new Result<>();
        if (!StringUtils.login(stuId, password)) {

            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 student可能为空
        Student student = studentDao.login(stuId, password);


        if (student == null) {

            return result.setResult(ResultEnum.LOGIN_FAIL);
        }
        //用 session记住用户的信息
        session.setAttribute("student", student);

        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    /**
     * 学生注册
     *
     * @param stuId    学生账号（学号）
     * @param password 学生密码
     * @param session  用户传入的session
     * @return 返回一个result对象 ，controller层将其转为json
     * @deprecated
     */
    @Deprecated
    @Override
    public Result<Boolean> register(String stuId, String password, HttpSession session) {

        return new Result<Boolean>().setResult(ResultEnum.USER_DO_FAIL);
    }

    /**
     * 工人注册时，写入数据库的一条记录
     *
     * @param stuId       工人的登录账号
     * @param stuName     工人的名字
     * @param stuTel      工人的电话号码
     * @param stuPassword 工人的账号密码
     * @param stuMail     工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false,将结果封装为bean对象
     */
    @Override
    public Result<Boolean> register(String stuId, String stuName, String stuTel, String stuPassword, String stuMail,HttpSession session) {
        Result<Boolean> result = new Result<>();
        if (!StringUtils.login(stuId, stuPassword)) {

            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 student可能为空
        boolean isSucess = studentDao.register(stuId,stuName,stuTel,stuPassword,stuMail);


        if (!isSucess) {

            return result.setResult(ResultEnum.USER_DO_FAIL);
        }



        return result.setResult(ResultEnum.COMMITED_SUCCESSFULLY);
    }


    /**
     * 学生在记得密码的情况下修改密码
     *
     * @param stuId    学生账号（学号）
     * @param password 学生密码
     * @return 返回一个result对象 ，controller层将其转为json
     */
    @Override
    public Result<Boolean> setPassword(String stuId, String password,HttpSession session) {
        return null;
    }

    /**
     * 学生在忘记密码的情况下修改密码
     *
     * @param stuId 学生账号
     * @return 返回一个result对象 ，controller层将其转为json
     */
    @Override
    public Result<Boolean> setPassword(String stuId,HttpSession session) {
        return null;
    }

    /**
     * 学生要修改的信息
     *
     * @param stuId      学生账号
     * @param password   学生密码
     * @param columnName 数据库的列名
     * @param value      数据库列对应的值
     * @return 返回一个记录学生修改是否成功的Result对象，后面处理为 json格式
     */
    @Override
    public Result<Boolean> modifyInformation(String stuId, String password, String columnName, String value,HttpSession session) {
        return null;
    }

    /**
     * 学生要修改的信息
     *
     * @param stuId       学生账号
     * @param password    学生密码
     * @param columnNames 修改数据库的多个列名
     * @param values      修改数据库多个列对应的值
     * @return 返回一个记录学生修改是否成功的Result对象，后面处理为 json格式
     */
    @Override
    public Result<Boolean> modifyInformation(String stuId, String password, String[] columnNames, String[] values,HttpSession session) {
        return null;
    }

    /**
     * 学生注销账户
     *
     * @param stuId    学生账号
     * @param password 学习密码
     * @return 返回是否注销成功
     */
    @Override
    public Result<Boolean> deleteStudent(String stuId, String password,HttpSession session) {
        return null;
    }

    /**
     * 学生查询正在申请的维修表单
     *
     * @param stuId 学生账号
     * @return 返回学习提交的所有表单的信息
     */
    @Override
    public Result<List<Form>> getFormList(String stuId,HttpSession session) {
        return null;
    }

    /**
     * 学生查询维修表单历史记录
     *
     * @param stuId 学生账号
     * @return 返回学习提交的所有表单的信息
     */
    @Override
    public Result<List<Form>> getOldFormList(String stuId,HttpSession session) {
        return null;
    }

    public StudentServiceImpl() {

    }
}
