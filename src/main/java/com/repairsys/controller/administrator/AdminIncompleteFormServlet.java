package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/11 15:31
 */
@WebServlet("/admin/incomplete/form")
public class AdminIncompleteFormServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(AdminIncompleteFormServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        logger.debug("requestBody{}", requestBody);
        //进入此页面时从cookie获取管理员id 查询对应name后再把管理员name设置到cookie
//        String adminId = CookieUtil.getCookie("adminId", request);
//        String adminName = adminService.getNameById(adminId);
//        CookieUtil.setCookie("adminName", adminName, response);
        Result result = adminService.getIncompleteForm(
                requestBody.getInteger("page"),
                requestBody.getInteger("limit"));
        int flag = 200;
        logger.debug("requestBody{}", requestBody);
        if (result.getCode() == flag) {
            logger.debug("查询成功{}", result);
        } else {
            logger.debug("查询失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
