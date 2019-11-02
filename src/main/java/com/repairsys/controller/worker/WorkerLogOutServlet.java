package com.repairsys.controller.worker;

import com.repairsys.controller.BaseServlet;
import com.repairsys.util.net.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/28 21:28
 */
@WebServlet("/worker/logout")
public class WorkerLogOutServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieUtil.cleanCookie("wKey", "", response);
        CookieUtil.cleanCookie("workerName", "", response);
        CookieUtil.cleanCookie("workerId", "", response);
        CookieUtil.cleanCookie("wToken", "", response);
        request.getSession().removeAttribute("workerId");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
