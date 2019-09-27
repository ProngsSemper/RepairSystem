package com.repairsys.controller;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 该类封装了发送方法，不配置路径
 * 具体业务由其子类类实现
 */
@WebServlet("/BaseServlet")
public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result res = (Result) request.getAttribute("result");
        //response把请求的数据响应给前台
        PrintWriter sender = response.getWriter();
        if (res != null) {
            sender.write(JSONObject.toJSONString(res));
            sender.flush();
            sender.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
