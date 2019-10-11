package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/6 14:24
 * <p>
 * 这里有个小坑，如果用 postman的话，你需要加一个来伪造
 */
@WebServlet("/student/form/history")
public class StudentHistoryServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name;
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        name = requestBody.getString("name");

        Result result = null;
        if (name != null && name.length() >= 9) {
            //尽量加多点约束，我们学校真实的学号长度>=9 的，
            AdminServiceImpl handler = ServiceFactory.getAdminService();
            result = handler.getAllFormByStudentName(name, requestBody.getInteger("page"), requestBody.getInteger("limit")
            );
            request.setAttribute("result", result);

        }
        System.out.println("发送");
        super.doPost(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
