package com.repairsys.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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

    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
