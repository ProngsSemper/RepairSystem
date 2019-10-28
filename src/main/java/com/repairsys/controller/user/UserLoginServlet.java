package com.repairsys.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.util.easy.EasyTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

    private static final String STU = "1";
    private static final String ADMIN = "2";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.debug("接收到用户登录请求");

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
                CookieUtil.setCookie("adminId","",response);
                CookieUtil.setCookie("workerId","",response);
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
