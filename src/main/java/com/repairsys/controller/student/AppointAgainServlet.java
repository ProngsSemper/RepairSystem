package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/23 8:52
 */
@WebServlet("/student/appoint")
public class AppointAgainServlet extends BaseServlet {
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();
    private static final Logger logger = LoggerFactory.getLogger(AppointAgainServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        Result result = studentService.appointAgain(requestBody.getString("appointDate"),
                requestBody.getInteger("appointment"),
                requestBody.getInteger("formId"));
        int flag = 201;
        if (result.getCode() == flag) {
            logger.debug("修改预约时间成功{}", result);
            String path = "/student/level";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
            requestDispatcher.forward(request, response);
        } else {
            logger.debug("修改预约时间失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
