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
        Boolean bool = (Boolean) req.getAttribute("pass");
        if (bool != null && bool) {
            chain.doFilter(req, resp);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        String t = request.getRequestURI();
        // logger.error(p);
        logger.error(t);
        if (t.length() <= 1) {
            chain.doFilter(request, resp);
            return;
        } else if (t.contains("/login")) {
            chain.doFilter(request, resp);
            return;
        } else if (t.contains("/index")) {
            chain.doFilter(request, resp);
            return;
        }else if(t.indexOf("/chat")>=0)
        {
            chain.doFilter(request,resp);
        }
        // boolean bool = t.indexOf("index")>=0||t.indexOf("login.jsp")

        HttpSession session = ((HttpServletRequest) req).getSession();
        Object obj1 = session.getAttribute("stuId");
        boolean talk = t.contains("/communication.html")||t.contains("/chat");
        boolean stuPage = t.contains("/firstPage.html")||talk;
        boolean adminPage = t.contains("/managerFirstPage.html")||talk||t.contains("/repair.html")||t.contains("/notice.html");
        boolean workerPage = t.contains("/workerPage.html");
        boolean b = false;
        if (obj1 != null && !("".equals(obj1))) {
            if (stuPage||t.contains("/student") || t.contains("/upload")) {
                logger.info("学生登录测试{}",obj1);
                b = true;

            }
        }
        Object obj2 = session.getAttribute("adminId");
        if (obj2 != null && !("".equals(obj2))) {
            if (adminPage||t.contains("/admin") || t.contains("/file")) {
                b = true;
            }
        }
        Object obj3 = session.getAttribute("workerId");
        if (obj3 != null && !("".equals(obj3))) {
            if (workerPage||t.contains("/worker")) {
                b = true;
            }
        }

        if (b) {
            chain.doFilter(req, resp);

        }

        logger.info("拦截请求");
        request.getRequestDispatcher("login.html").forward(request,resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void destroy() {
    }

}
