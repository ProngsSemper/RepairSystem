package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/9/29 14:08
 */
@WebServlet("student/information")
public class StudentAlterServlet extends BaseServlet {
    private static Logger logger = LoggerFactory.getLogger(StudentAlterServlet.class);
    private StudentService studentService = ServiceFactory.getStudentService();
    //TODO: 需要session判断用户是否注册，如果没有注册，要用户重定向到首页


    /**
     *
     * 用户登录后，需要修改密码和修改个人信息
     *
     * */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String column = requestBody.getString("column");
        String columnValue = requestBody.getString("columnValue");
        Result result = studentService.modifyInformation(

                requestBody.getString("stuId")
                ,requestBody.getString("stuPassword")

                ,column,columnValue,session);
        logger.debug("返回修改信息{}",result);



    }
   /**
    *
    * 用户个人信息获取
    *
    * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     *
     *
     * 用户在首页忘记密码，需要重置密码
     *
     * */

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
