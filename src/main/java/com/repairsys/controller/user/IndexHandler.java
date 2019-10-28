package com.repairsys.controller.user;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Worker;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.net.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/28 9:41
 */
@WebServlet("/index.do")
public class IndexHandler extends BaseServlet {
    private final AdminDaoImpl adminDao = (AdminDaoImpl) DaoFactory.getAdminDao();
    private final WorkerDaoImpl workerDao = (WorkerDaoImpl) DaoFactory.getWorkerDao();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
