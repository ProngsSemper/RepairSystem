package com.repairsys.filter;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Student;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Prongs
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Student student = (Student) req.getSession().getAttribute("student");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        Worker worker = (Worker) req.getSession().getAttribute("worker");

        if (student == null || admin == null || worker == null) {
            HttpServletResponse resp = (HttpServletResponse) response;
            PrintWriter out = resp.getWriter();
            Result result = new Result();
            result.setResult(ResultEnum.LOGIN_FAIL);
            out.print(JSONObject.toJSONString(result));
            out.flush();
            out.close();
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
