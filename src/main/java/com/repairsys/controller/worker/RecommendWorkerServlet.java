package com.repairsys.controller.worker;

import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 简单的推荐工人的接口，通过给 worker和 wTime 添加属性 score，排序时比较score的大小决定其顺序
 *
 * @Author lyr
 * @create 2019/10/11 0:59
 */
@WebServlet("/worker/recommendation")
public class RecommendWorkerServlet extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(RecommendWorkerServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("推荐算法");
        WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
        Result res = workerService.getSortedWorkerList();
        request.setAttribute("result", res);
        logger.debug("发送");
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
