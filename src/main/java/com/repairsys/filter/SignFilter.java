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

        HttpServletRequest request = (HttpServletRequest) req;
        String t = request.getRequestURI();
        if(t.length()<=1)
        {
            chain.doFilter(request,resp);
            return;
        }
        HttpServletResponse response = (HttpServletResponse) resp;

        response.setDateHeader("Expires", -1);
        response.setHeader("Cache_Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        logger.info(t);

        boolean pass = t.contains("/comm");
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (pass || t.contains("managerFirstPage.h") || t.contains("repair.h") || t.contains("notice.h") || t.contains("/board")) {
            Object admin = session.getAttribute("adminId");
            if (admin != null) {
                chain.doFilter(request, resp);
                return;
            }
        } else if (t.contains("worker")) {
            Object worker = session.getAttribute("workerId");
            if (worker != null) {

                chain.doFilter(req, resp);
                return;
            }
        } else {
            Object stu = session.getAttribute("stuId");
            logger.info("{}", stu);
            boolean login = stu != null;
            if (login) {
                chain.doFilter(request, resp);
                return;
            }
        }
        if (pass && session.getAttribute("stuId") != null) {
            chain.doFilter(request, resp);
            return;

        }

        boolean b = t.contains("login.html") || t.contains(".do") || t.contains(".jsp");
        if (b) {
            chain.doFilter(request, resp);

        } else {
            ((HttpServletResponse) resp).sendRedirect("login.html");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void destroy() {
    }

}
