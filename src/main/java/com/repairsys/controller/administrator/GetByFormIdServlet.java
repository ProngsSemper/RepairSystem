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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/9/30 20:28
 *
 * 估计 表单号查询信息
 */
@WebServlet("/admin/formId")
public class GetByFormIdServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(GetByFormIdServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        Result result = adminService.getByFormId(requestBody.getString("formId"));
        int flag = 200;
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
