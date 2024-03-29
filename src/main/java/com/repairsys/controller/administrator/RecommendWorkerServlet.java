package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.RecommendedWorker;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/17 16:20
 */
@WebServlet("/admin/worker")
public class RecommendWorkerServlet extends BaseServlet {
    private WorkerServiceImpl workerService = ServiceFactory.getWorkerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");

        Result<List<RecommendedWorker>> res = workerService.getSuitableWorkerListPlus(jsonObject.getSqlDate("date"),
                jsonObject.getInteger("hour"),
                jsonObject.getString("wType"),
                jsonObject.getString("location")

        );
        request.setAttribute("result", res);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
