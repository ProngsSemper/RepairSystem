package com.repairsys.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.repairsys.bean.vo.Result;

import javax.servlet.ServletException;
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

public abstract class BaseServlet extends HttpServlet {
    /**
     * @author lyr
     * @date 2019/9/27
     * BaseServlet 里面的方法都不要配置路径，只是给之类使用而已
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result res = (Result) request.getAttribute("result");
        //response把请求的数据响应给前台
        PrintWriter sender = response.getWriter();

        if (res != null) {

            sender.write(JSONObject.toJSONStringWithDateFormat(res, "yyyy-MM-dd HH", SerializerFeature.WriteDateUseDateFormat));
            sender.flush();
            sender.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
