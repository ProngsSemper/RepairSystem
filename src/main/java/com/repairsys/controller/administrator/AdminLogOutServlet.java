package com.repairsys.controller.administrator;

import com.repairsys.controller.BaseServlet;
import com.repairsys.util.net.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/28 21:24
 */
@WebServlet("/admin/logout")
public class AdminLogOutServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieUtil.cleanCookie("adminId", "", response);
        CookieUtil.cleanCookie("adminName", "", response);
        CookieUtil.cleanCookie("adminToken", "", response);
        response.sendRedirect("../index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
