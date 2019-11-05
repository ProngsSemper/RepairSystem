package com.repairsys.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/11/1 14:37
 */
@WebFilter(filterName = "WorkerFilter")
public class WorkerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(WorkerFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;

        boolean b = request.getSession().getAttribute("workerId") == null;

        if (!b) {
            resp.setContentType("application/json");
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
