package com.repairsys.controller.developer;

import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/5 12:47
 */
@WebServlet(name = "Servlet")
public class WorkerTest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result result = new Page<List<Worker>>();
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
