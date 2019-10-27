package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
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
        System.out.println(jsonObject.getString("date").replace("月","-").replace("日","-"));
        String s = (jsonObject.getString("date").replace("月","-").replace("日","-"));
        System.out.println(s);
        Result<List<Worker>> res = workerService.getSuitableWorkerList(jsonObject.getSqlDate("date"),
                jsonObject.getInteger("hour"),
                jsonObject.getString("wType"));
        System.out.println(res);
        request.setAttribute("result",res);
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
