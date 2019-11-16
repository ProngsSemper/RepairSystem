package com.repairsys.controller.user;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Worker;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr, Prongs
 * @create 2019/10/28 9:41
 */
@WebServlet("/index.do")
public class IndexHandler extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(IndexHandler.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminToken = CookieUtil.getCookie("adminToken", request);
        String workerToken = null;
        workerToken = CookieUtil.getCookie("wToken", request);
        System.out.println(adminToken);

        request.setAttribute("pass", "1");
        if (adminToken != null) {
            Admin admin = AdminDaoImpl.getInstance().existsToken(adminToken);
            if (admin != null) {
                String adminId = CookieUtil.getCookie("adminId", request);
                boolean b = admin.getAdminId().equals(adminId);
                if (b) {
                    request.getSession().setAttribute("adminId", adminId);
                    logger.debug(adminId);

                    request.getRequestDispatcher("managerFirstPage.html").forward(request, response);
                    return;
                }

            }

        }
        if (workerToken != null) {
            Worker worker = WorkerDaoImpl.getInstance().existToken(workerToken);
            if (worker != null) {
                String workerId = CookieUtil.getCookie("workerId", request);
                boolean b = worker.getwId().equals(workerId);
                if (b) {
                    request.getSession().setAttribute("workerId", workerId);
                    request.getRequestDispatcher("workerPage.html").forward(request, response);
                    return;
                }

            }

        }

        boolean isStu = request.getSession().getAttribute("stuId") != null;
        if (isStu) {
            request.getRequestDispatcher("firstPage.html").forward(request, response);
            return;
        } else {
            boolean isAdmin = request.getSession().getAttribute("adminId") != null;
            if (isAdmin) {
                request.getRequestDispatcher("managerFirstPage.html").forward(request, response);
                return;
            }
        }

        request.setAttribute("pass", "1");

        request.getRequestDispatcher("login.html").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
