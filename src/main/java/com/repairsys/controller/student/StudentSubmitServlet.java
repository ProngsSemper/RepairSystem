package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @Author lyr
 * @create 2019/10/9 20:39
 * <p>
 * 学生提交报修表单
 */
@WebServlet("/student/submission/form")
public class StudentSubmitServlet extends BaseServlet {
    public static final Logger logger = LoggerFactory.getLogger(StudentSubmitServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // apply(String stuId, int code, String formMsg, Date formDate, String stuMail, String photoId)
        logger.debug("收到申请表单请求");
        logger.debug("学生登录 ");

        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        Result res = ServiceFactory.getStudentService().applyForm(
                requestBody.getString("stuId"),
                0,
                requestBody.getString("formMsg"),
                new Timestamp(System.currentTimeMillis()),
                requestBody.getString("stuMail"),
                requestBody.getString("photoId"),
                requestBody.getString("room"),
                requestBody.getString("stuName"),
                requestBody.getString("stuPhone"),
                requestBody.getString("wType")
        );
        request.setAttribute("result", res);
        super.doPost(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
