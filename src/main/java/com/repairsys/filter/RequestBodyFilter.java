package com.repairsys.filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author 林洋锐，Prongs
 * @date 2019/9/21
 * 主要对请求体进行预处理
 * 对于前台发送来的 json数据，我们用过滤器统一解析为 JsonObject对象，Servlet里面只需要得到这个对象即可用
 */
@WebFilter(filterName = "RequestBodyFilter")
public class RequestBodyFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 读取 request 的字符流
        BufferedReader br = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String content;

        //需要把字符流转化为 字符串
        while ((content = br.readLine()) != null) {
            jsonBuilder.append(content);
        }

        //判断 json的内容不为空
        if (jsonBuilder.length() > 0) {
            //使用 阿里提供的 api
            //将 json数据读取为一行，转为JsonObject对象
            JSONObject json = JSONObject.parseObject(jsonBuilder.toString());
            request.setAttribute("requestBody", json);
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
