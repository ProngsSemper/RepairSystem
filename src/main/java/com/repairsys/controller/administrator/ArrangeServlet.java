package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.net.CookieUtil;
import com.repairsys.util.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/17 20:01
 * 管理员将报修单安排给某个工人，并将报修单状态改成已联系工人状态，
 * 并记录是哪个管理员进行的操作
 * 给学生发送通知邮件
 */
@WebServlet("/admin/arrangement/form")
public class ArrangeServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(ArrangeServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("安排工人");
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        JSONObject mailRequestBody = (JSONObject) request.getAttribute("requestBody");
        String stuMail = mailRequestBody.getString("stuMail");
        String adminId = (String)request.getSession().getAttribute("adminId");
        Result result = adminService.arrange(requestBody.getInteger("wKey"),
                adminId,
                TimeUtil.getTime(requestBody.getInteger("day")),
                requestBody.getInteger("hour"),
                requestBody.getInteger("formId")
        );
        int flag = 201;
        if (result.getCode() == flag) {
//            todo:可能有逻辑问题，到时检查
            String wKey = requestBody.getString("wKey");
            int hour = requestBody.getInteger("hour");
            String date = TimeUtil.getTime(requestBody.getInteger("day"));
            WorkerScheule.getInstance().setTime(date, hour, wKey);
            logger.debug("修改报修单状态成功{}", result);
            //发送通知邮件
            try {
                Result mailResult = adminService.senMail(stuMail,
                        mailRequestBody.getInteger("day"),
                        mailRequestBody.getInteger("hour"),
                        mailRequestBody.getString("wTel")
                );
                int mailFlag = 200;
                if (mailResult.getCode() == mailFlag) {
                    logger.debug("邮件发送成功{}", mailResult);
                } else {
                    logger.debug("邮件发送失败{}", mailResult);
                }
                request.setAttribute("mailResult", mailResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.debug("修改报修单状态失败{}", result);
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
