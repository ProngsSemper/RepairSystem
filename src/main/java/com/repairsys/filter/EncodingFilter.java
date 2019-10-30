package com.repairsys.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 设置编码 统一为 UTF-8
 * </p>
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    /**
     * 默认需要放行的资源
     */
    private static final String[] ARRAY = {"/", ".png", ".jpg", ".css", ".js", ".gif", ".html", ".ico"};
    private static final String[] UI = {"index", "?", "woff", "limit", ".html", ".jsp", "img"};
    private static final String HTML = "htm";
    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String t = request.getRequestURI();
        for (String i : ARRAY) {
            if (t.endsWith(i)) {
                logger.debug("放行静态资源 {}", t);
                // req.setAttribute("pass",true);
                chain.doFilter(req, resp);
                return;
            }
        }
        for (String i : UI) {
            if (t.lastIndexOf(i) >= 0) {
                // req.setAttribute("pass",true);
                chain.doFilter(req, resp);
                return;
            }
        }

        logger.debug("进行过滤处理1");

        // 设置请求的编码为 utf-8
        req.setCharacterEncoding("UTF-8");
        //设置响应编码 为 utf-8 ,声明为json格式

        if (t.indexOf(HTML) > 0) {

            resp.setContentType("text/html;charset=UTF-8");

        } else {
            resp.setContentType("application/json;charset=UTF-8");
        }
        resp.setCharacterEncoding("UTF-8");

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
