package com.repairsys.controller.user;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Worker;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.impl.admin.AdminServiceImpl;
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
        String adminToken = CookieUtil.getCookie("adminToken",request);
        System.out.println(adminToken);
        if(adminToken!=null)
        {
           Admin admin =  AdminDaoImpl.getInstance().existsToken(adminToken);

           if(admin!=null)
           {
               String adminId =  CookieUtil.getCookie("adminId",request);
              boolean b=  admin.getAdminId().equals(adminId);
              if(b)
              {
                  request.getSession().setAttribute("adminId",adminId);
                  request.getRequestDispatcher("managerFirstPage.html").forward(request,response);
                  return;
              }

           }

        }else
        {
            
        }
        response.sendRedirect("login.html");


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
