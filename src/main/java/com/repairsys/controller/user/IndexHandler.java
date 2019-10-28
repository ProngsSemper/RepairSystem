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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String adminToken = CookieUtil.getCookie("adminToken",request);
        String adminId = CookieUtil.getCookie("adminId",request);
        String wToken = CookieUtil.getCookie("wToken",request);
        String workerId = CookieUtil.getCookie("workerId",request);
        EasyTool.debug(100,adminId,adminToken,workerId,wToken);
        if ((!"".equals(adminId) )||(!"".equals(workerId))) {


            if ((adminToken != null && adminId != null) || (wToken != null && workerId != null)) {
                Admin admin = adminDao.getToken(adminId);
                Worker worker = workerDao.getToken(workerId);
                if (admin != null && admin.getAdminToken().equals(adminToken)) {


                    System.out.println(11111111);
                    request.getSession().setAttribute("adminId", adminId);
                    request.getRequestDispatcher("../managerFirstPage.html").forward(request, response);
                    return;
                } else if (worker != null && worker.getwToken().equals(wToken)) {


                    System.out.println(222222222);

                    request.getSession().setAttribute("workerId", workerId);
                    request.getRequestDispatcher("../workerPage.html").forward(request, response);
                    return;
                }
            }else{
                response.sendRedirect("../login.html");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
