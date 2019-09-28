package com.repairsys.controller;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.StudentService;
import com.repairsys.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Prongs
 */
@WebServlet("/student")
public class StudentLoginServlet extends BaseServlet {

    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        Result result = studentService.login(requestBody.getString("stuId"),
                requestBody.getString("stuPassword"),
                session);
        System.out.println(result.getCode());

        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
