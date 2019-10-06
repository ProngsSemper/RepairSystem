package com.repairsys.controller.student.page;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/6 14:24
 *
 * 这里有个小坑，如果用 postman的话，你需要加一个来伪造
 *
 */
@WebServlet("/student/form/history")
public class StudentHistoryServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String id=null;
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        id = requestBody.getString("id");

        Result result = null;
        if(id!=null&&id.length()>=9)
        {
            //尽量加多点约束，我们学校真实的学号长度>=9 的，
            AdminServiceImpl handler = ServiceFactory.getAdminService();
            result = handler.getAllFormByStudentId(requestBody.getInteger("page"),requestBody.getInteger("limit")
            ,id

            );
            request.setAttribute("result",result);

        }
        System.out.println("发送");
        super.doPost(request, response);




    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
