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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/6 14:24
 * <p>
 * 这里有个小坑，如果用 postman的话，你需要加一个来伪造
 */
@WebServlet("/student/incomplete/history/form")
public class StudentIncompleteHistoryServlet extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(StudentIncompleteHistoryServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String stuId = CookieUtil.getCookie("stuId", request);
        Result result;
        if (stuId != null && stuId.length() >= 9) {
            //尽量加多点约束，我们学校真实的学号长度>=9 的，
            StudentServiceImpl handler = ServiceFactory.getStudentService();
            result = handler.getIncompleteFormByStudentId(
                    stuId,
                    requestBody.getInteger("page"),
                    requestBody.getInteger("limit")
            );
            int flag = 200;
            if (result.getCode() == flag) {
                logger.debug("查询成功{}", result);
            } else {
                logger.debug("查询失败{}", result);
            }
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
