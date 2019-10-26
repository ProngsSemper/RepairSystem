package com.repairsys.controller.worker;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
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
 * @date 2019/10/3 11:48
 */
@WebServlet("/worker/incomplete/stuName")
public class GetIncompleteFormByStudentNameServlet extends BaseServlet {
    private final WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
    private final FormListDaoImpl formListDao = (FormListDaoImpl) DaoFactory.getFormDao();
    private static final Logger logger = LoggerFactory.getLogger(GetIncompleteFormByStudentNameServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String workerId = CookieUtil.getCookie("workerId", request);
        int wKey = formListDao.getWorkerKeyById(workerId);
        Result result = workerService.getAllIncompleteFormByStudentName(requestBody.getString("stuName"),
                wKey,
                requestBody.getInteger("page"),
                requestBody.getInteger("limit")
        );
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
