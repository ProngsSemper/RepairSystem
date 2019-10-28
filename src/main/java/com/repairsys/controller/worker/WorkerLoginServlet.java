package com.repairsys.controller.worker;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
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
 * @date 2019/9/29 16:07
 */
@WebServlet("/worker/login")
public class WorkerLoginServlet extends BaseServlet {
    private final WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
    private final WorkerDaoImpl workerDao = (WorkerDaoImpl) DaoFactory.getWorkerDao();
    private static final Logger logger = LoggerFactory.getLogger(WorkerLoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String workerId = requestBody.getString("id");
        int loginSuccess = 200;
        Result result = workerService.login(workerId,
                requestBody.getString("password"),
                session);
        logger.debug("工人登录信息{}", result);
        request.setAttribute("result", result);
        //登录成功设置cookie
        if (result.getCode() == loginSuccess) {
            String wKey = String.valueOf(workerDao.getWorkerKeyById(workerId).getwKey());
            CookieUtil.setToken("workerId", workerId, response);
            CookieUtil.setCookie("wKey", wKey, response);
            String wToken = workerDao.getToken(requestBody.getString("id")).getwToken();
            CookieUtil.setToken("wToken", wToken, response);
            response.addHeader("identity", "worker");
        }
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
