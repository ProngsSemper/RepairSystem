package com.repairsys.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.net.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/3 20:14
 */
@WebServlet({"/user/login"})
public class UserLoginServlet extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);
    private final AdminDaoImpl adminDao = (AdminDaoImpl) DaoFactory.getAdminDao();
    private final WorkerDaoImpl workerDao = (WorkerDaoImpl) DaoFactory.getWorkerDao();
    private static final String STU = "1";
    private static final String ADMIN = "2";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.debug("接收到用户登录请求");

        // JSONObject jsonObject1 = (JSONObject) request.getAttribute("requestBody");
        // if(jsonObject1!=null)
        // {
        //     String b = jsonObject1.getString("vcode");
        //     if(b!=null&&b.length()>0)
        //     {
        //         // request.getSession().
        //         request.getSession().removeAttribute("adminId");
        //
        //     }
        // }
        // System.out.println(123);




        String adminToken = CookieUtil.getCookie("adminToken", request);
        String adminId = CookieUtil.getCookie("adminId", request);
        String wToken = CookieUtil.getCookie("wToken", request);
        String workerId = CookieUtil.getCookie("workerId", request);
        if ((adminToken != null && adminId != null) || (wToken != null && workerId != null)) {
            Admin admin = adminDao.getToken(adminId);
            Worker worker = workerDao.getToken(workerId);
            if (admin != null && admin.getAdminToken().equals(adminToken)) {
                String aId = CookieUtil.getAdminId(request);
                request.getSession().setAttribute("adminId",aId);
                request.getRequestDispatcher("../managerFirstPage.html").forward(request, response);
                return;
            }
            else if (worker != null && worker.getwToken().equals(wToken)) {
                request.getRequestDispatcher("../workerPage.html").forward(request, response);
                return;
            }
        }
        boolean b = EasyTool.compareToCode(request);
        if (!b) {
            JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
            if (jsonObject == null) {
                request.getRequestDispatcher("../login.html").forward(request, response);
                return;
            }
            logger.debug("验证码错误");
            Result<Boolean> result = new Result<>();
            result.setResult(ResultEnum.CODE_FALSE);
            request.setAttribute("result", result);
            super.doPost(request, response);
        } else {
            logger.debug("验证码正确");
            JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
            String value = jsonObject.getString("radio");
            logger.debug("{}", value);
            if (STU.equals(value)) {
                logger.debug("学生");
                request.getRequestDispatcher("/student/login").forward(request, response);

            } else if (ADMIN.equals(value)) {
                logger.debug("管理员");
                request.getRequestDispatcher("/admin/login").forward(request, response);
            } else {
                logger.debug("工人");
                request.getRequestDispatcher("/worker/login").forward(request, response);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
