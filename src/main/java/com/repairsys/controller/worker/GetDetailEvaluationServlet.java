package com.repairsys.controller.worker;

import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
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
 * @date 2019/10/23 20:11
 */
@WebServlet("/worker/evaluation/detail")
public class GetDetailEvaluationServlet extends BaseServlet {
    private final WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
    private static final Logger logger = LoggerFactory.getLogger(GetDetailEvaluationServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int wKey = Integer.parseInt(CookieUtil.getCookie("wKey", request));
        Result result = workerService.getDetailEvaluation(wKey);
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
