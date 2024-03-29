package com.repairsys.controller.worker;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
import com.repairsys.util.mail.MailFactory;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Prongs
 * @date 2019/10/6 14:45
 */
@WebServlet("/worker/queryCode")
public class UpdateQueryCodeServlet extends BaseServlet {
    private final WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
    private static final Logger logger = LoggerFactory.getLogger(UpdateQueryCodeServlet.class);
    private final LinkedBlockingQueue<Runnable> queue = MailFactory.getInstance().getQueue();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String stuMail = requestBody.getString("stuMail");
        int wKey = Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie("wKey", request)));
        int queryCode = requestBody.getInteger("queryCode");
        int flag = 201;
        int finishCode = 2;
        int errorCode = -1;
        String day = requestBody.getString("day");
        String hour = requestBody.getString("hour");

        Worker worker = WorkerDaoImpl.getInstance().getWorkerTelByKey(wKey);
        String wTel = worker.getwTel();
        Result result = workerService.updateQueryCode(queryCode
                , requestBody.getInteger("formId"));
        if (result.getCode() == flag) {
            logger.debug("修改成功{}", result);
            //确认为维修完成时 发送维修完成邮件
            if (finishCode == queryCode) {
                WorkerScheule.getInstance().updateWtime(day, hour, wKey);

                Runnable t = () -> {

                    try {
                        MailUtil.sendFinishMail(stuMail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                queue.add(t);
                //维修有问题时 发送error邮件
            } else if (errorCode == queryCode) {
                WorkerScheule.getInstance().updateWtime(day, hour, wKey);
                Runnable t = () -> {

                    try {
                        MailUtil.sendErrorMail(stuMail, wTel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                queue.add(t);

            }
            MailFactory.getInstance().checkAndRun();
        } else {
            logger.debug("修改失败{}", result);
        }
        request.setAttribute("result", result);

        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
