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

        logger.debug(" session 的id是： " + session.getId());

        if(result.getCode()==200)
        {
            response.addCookie(new Cookie("id",requestBody.getString("id")));
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
