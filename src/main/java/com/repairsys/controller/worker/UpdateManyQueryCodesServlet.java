package com.repairsys.controller.worker;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.net.CookieUtil;
import com.repairsys.util.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author Prongs
 * @date 2019/11/5 20:16
 */
@WebServlet("/worker/multi/queryCode")
public class UpdateManyQueryCodesServlet extends BaseServlet {
    private final WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
    private static final Logger logger = LoggerFactory.getLogger(UpdateQueryCodeServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        int wKey = Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie("wKey", request)));
        Worker worker = WorkerDaoImpl.getInstance().getWorkerTelByKey(wKey);
        String wTel = worker.getwTel();
        String content;
        int flag = 201;
        int finishCode = 2;
        int errorCode = -1;
        while ((content = br.readLine()) != null) {
            jsonBuilder.append(content);
        }
        List<Form> formList = JSONObject.parseArray(jsonBuilder.toString().substring(0,jsonBuilder.length()), Form.class);
        for (Form form : formList) {
            String stuMail = form.getStuMail();
            int queryCode = form.getQueryCode();
            SimpleDateFormat df = new SimpleDateFormat("dd");
            String fakeDay = df.format(form.getAppointDate());
            String day = TimeUtil.getTime(Integer.parseInt(fakeDay));
            String hour = String.valueOf(form.getAppointment());
            Result result = workerService.updateQueryCode(queryCode
                    , form.getFormId());
            //确认为维修完成时 发送维修完成邮件
            if (finishCode == queryCode) {
                WorkerScheule.getInstance().updateWtime(day, hour, wKey);
                try {
                    MailUtil.sendFinishMail(stuMail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //维修有问题时 发送error邮件
            } else if (errorCode == queryCode) {
                WorkerScheule.getInstance().updateWtime(day, hour, wKey);
                try {
                    MailUtil.sendErrorMail(stuMail, wTel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (result.getCode() == flag) {
                logger.debug("修改成功{}", result);
            } else {
                logger.debug("修改失败{}", result);
            }
            request.setAttribute("result", result);
        }
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
