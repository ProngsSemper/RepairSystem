package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/9/29
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginServlet.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        Result result = adminService.login(requestBody.getString("id"),
                requestBody.getString("password"),
                session);
        logger.debug("登录信息{}", result);
        request.setAttribute("result", result);
        String remember = requestBody.getString("remember");
        String flag = "true";
        int successCode = 200;
        //五年内记住密码
        if (result.getCode() == successCode && flag.equals(remember)) {
            Cookie rememberCookie = new Cookie("remember", remember);
            rememberCookie.setMaxAge(5 * 360 * 24 * 60 * 60);
            Cookie idCookie = new Cookie("id", requestBody.getString("id"));
            idCookie.setMaxAge(5 * 360 * 24 * 60 * 60);
            Cookie passwordCookie = new Cookie("password", requestBody.getString("password"));
            passwordCookie.setMaxAge(5 * 360 * 24 * 60 * 60);
        } else {
            //清空cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
