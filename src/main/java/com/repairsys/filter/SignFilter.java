package com.repairsys.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/30 14:59
 */
@WebFilter(filterName = "SignFilter")
public class SignFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SignFilter.class);




    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // String p = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/";

        HttpServletRequest request = (HttpServletRequest) req;
        String t = request.getRequestURI();
        // logger.error(p);
        logger.error(t);
        if(t.length()<=1)
        {
            chain.doFilter(request,resp);
            return;
        }
        else if(t.indexOf("/login")>=0)
        {
            chain.doFilter(request,resp);
            return;
        }else if(t.indexOf("/index")>=0)
        {
            chain.doFilter(request,resp);
            return;
        }
        // boolean bool = t.indexOf("index")>=0||t.indexOf("login.jsp")

        HttpSession session = ((HttpServletRequest)req).getSession();
        Object obj1 = session.getAttribute("stuId");
        boolean b = false;
        if(obj1!=null)
        {
            b = true;
        }else{
            Object obj2 = session.getAttribute("adminId");
            if(obj2!=null)
            {
                b=true;
            }else{
                Object obj3 = session.getAttribute("workerId");
                if(obj3!=null)
                {
                    b = true;
                }
            }
        }

        if(b)
        {
            chain.doFilter(req, resp);

        }

        logger.info("拦截请求");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }






    @Override
    public void destroy() {
    }

}
