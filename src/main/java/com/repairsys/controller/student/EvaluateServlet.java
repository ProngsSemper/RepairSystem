package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/22 23:06
 * 学生评价工人
 */
@WebServlet("/student/evaluation")
public class EvaluateServlet extends HttpServlet {
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();
    private static final Logger logger = LoggerFactory.getLogger(EvaluateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        //下面这个evaluation只能传good、mid、bad分别表示好评、中评、差评
        Result result = studentService.evaluate(requestBody.getString("evaluation"),
                requestBody.getInteger("wKey"));
        int flag = 201;
        if (result.getCode() == flag) {
            logger.debug("评价成功{}", result);
        } else {
            logger.debug("评价失败{}", result);
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
