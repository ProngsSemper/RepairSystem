package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//写完了哦

/**
 * @Author lyr
 * @create 2019/9/29 14:08
 */
@WebServlet("/student/login")
public class StudentLoginServlet extends BaseServlet {
    private static Logger logger = LoggerFactory.getLogger(StudentLoginServlet.class);
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("学生登录 ");
        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        int loginSuccess = 200;
        String stuId = requestBody.getString("id");
        Result result = studentService.login(
                stuId,
                requestBody.getString("password"),
                session
        );
        String stuName = studentService.stuName;
        logger.info("学生登录信息在这里  {}", result);
        request.setAttribute("result", result);
        request.setAttribute("stuName", stuName);
        logger.debug(" session 的id是： " + session.getId());
        //登录成功设置cookie
        if (result.getCode() == loginSuccess) {
            CookieUtil.setCookie("stuName", stuName, response);
            CookieUtil.setCookie("stuId", stuId, response);
            response.addHeader("identity", "student");
        }
        try {
            super.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户个人信息获取
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        doPost(request, response);
    }

}
