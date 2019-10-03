package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
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
        Result result = studentService.login(
                requestBody.getString("id"),
                requestBody.getString("password"),
                session
        );
        logger.info("学生登录信息在这里  {}", result);
        request.setAttribute("result", result);
        String remember = requestBody.getString("remember");
        String flag = "true";
        int successCode = 200;
        if (result.getCode() == successCode && flag.equals(remember)) {
            Cookie rememberCookie = new Cookie("remember", remember);
            //七天内记住密码
            rememberCookie.setMaxAge(7 * 24 * 60 * 60);
            Cookie idCookie = new Cookie("id", requestBody.getString("id"));
            idCookie.setMaxAge(7 * 24 * 60 * 60);
            Cookie passwordCookie = new Cookie("password", requestBody.getString("password"));
            passwordCookie.setMaxAge(7 * 24 * 60 * 60);
        } else {
            //清空cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        logger.debug(" session 的id是： " + session.getId());

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
