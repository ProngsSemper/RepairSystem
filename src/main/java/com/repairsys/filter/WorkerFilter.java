package com.repairsys.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/11/1 14:37
 */
@WebFilter(filterName = "WorkerFilter")
public class WorkerFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest)req;
        boolean b = request.getSession().getAttribute("workerId")==null;

        if(!b)
        {
            chain.doFilter(req, resp);
        }else{
            HttpServletResponse response = (HttpServletResponse)resp;
            response.sendRedirect("login.html");
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
