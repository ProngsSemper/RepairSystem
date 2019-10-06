package com.repairsys.controller.worker.page;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Page;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.worker.WorkerListDaoImpl;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.worker.WorkerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/5 22:54
 */
@WebServlet("/worker/form/stuId")
public class GetPageByStudentId extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody =  (JSONObject) request.getAttribute("requestBody");

        WorkerServiceImpl workerService = ServiceFactory.getWorkerService();
        Page p = (Page) workerService.getAllFormByStudentId(
            requestBody.getString("stuId"),
            requestBody.getInteger("page")
            ,requestBody.getInteger("limit")
        );
        request.setAttribute("result",p);
        super.doPost(request,response);



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
