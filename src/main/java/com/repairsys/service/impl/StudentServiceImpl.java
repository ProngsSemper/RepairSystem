package com.repairsys.service.impl;

import com.repairsys.bean.entity.Student;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.StudentDao;
import com.repairsys.service.StudentService;
import com.repairsys.util.string.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * @author Prongs
 */
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = DaoFactory.getStudentDao();

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

        session.setAttribute("student", student);

        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    public StudentServiceImpl() {

    }
}
