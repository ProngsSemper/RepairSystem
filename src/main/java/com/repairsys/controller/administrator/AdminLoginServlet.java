package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/9/29
 * <p>
 * 登录
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private final AdminDaoImpl adminDao = (AdminDaoImpl) DaoFactory.getAdminDao();
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        int loginSuccess = 200;
        String adminId = requestBody.getString("id");
        Result<Boolean> result = adminService.login(adminId,
                requestBody.getString("password"),
                session);
        String adminToken = adminDao.getToken(requestBody.getString("id")).getAdminToken();
        CookieUtil.setToken("adminToken", adminToken, response);
        logger.debug("管理员登录信息{}", result);
        request.setAttribute("result", result);
        //登录成功设置cookie
        if (result.getCode() == loginSuccess) {
            CookieUtil.setToken("adminId", adminId, response);
            response.addHeader("identity", "admin");
            session.setAttribute("adminId", adminId);
        }
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
