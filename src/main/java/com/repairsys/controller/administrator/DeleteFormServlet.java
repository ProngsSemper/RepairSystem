package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.form.FormDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author Prongs
 * @date 2019/10/17 19:44
 * 管理员删除报修单，并向学生发送通知。
 */
@WebServlet("/admin/delete/form")
public class DeleteFormServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private final FormDaoImpl formDao = (FormDaoImpl) DaoFactory.getFormDao();
    private static final Logger logger = LoggerFactory.getLogger(DeleteFormServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        int formId = requestBody.getInteger("formId");
        Form form = formDao.queryByFormId(formId);
        String stuMail = form.getStuMail();
        String formMsg = form.getFormMsg();
        String adminName = URLDecoder.decode(CookieUtil.getCookie("adminName", request));
        try {
            MailUtil.sendDeleteMail(stuMail, formMsg, adminName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Result result = adminService.deleteOne(formId);
        int flag = 201;
        if (result.getCode() == flag) {
            logger.debug("删除成功{}", result);
        } else {
            logger.debug("删除失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
