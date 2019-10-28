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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/15 20:49
 * 学生确认报修完成
 */
@WebServlet("/student/confirmation/form")
public class ConfirmFormServlet extends BaseServlet {
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();
    private static final Logger logger = LoggerFactory.getLogger(ConfirmFormServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        Result result = studentService.confirm(requestBody.getInteger("formId"));
        int flag = 201;
        if (result.getCode() == flag) {
            logger.debug("确认成功{}", result);
        } else {
            logger.debug("确认失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
