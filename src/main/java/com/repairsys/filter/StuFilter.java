package com.repairsys.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/11/1 14:42
 */
@WebFilter(filterName = "StuFilter")
public class StuFilter implements Filter {
    @Override
    public void destroy() {
    }

    private static final Logger logger = LoggerFactory.getLogger(StuFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest request = (HttpServletRequest)req;
        Object obj = request.getAttribute("static");
        if(obj!=null)
        {
            logger.info("放行静态资源");
            chain.doFilter(request,resp);
            return;
        }


        boolean b = request.getSession().getAttribute("stuId")==null;
        String t = request.getRequestURI();
        logger.info(t);

        if(!b)
        {
            resp.setContentType("application/json");
            chain.doFilter(req, resp);
        }
        logger.error(t);

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
