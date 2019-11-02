package com.repairsys.controller.student;

import com.repairsys.controller.BaseServlet;
import com.repairsys.util.net.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/28 21:11
 */
@WebServlet("/student/logout")
public class StudentLogOutServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieUtil.cleanCookie("stuId","",response);
        CookieUtil.cleanCookie("stuName","",response);
        request.getSession().removeAttribute("stuId");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
