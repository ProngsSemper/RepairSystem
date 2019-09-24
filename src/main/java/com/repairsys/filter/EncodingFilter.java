package com.repairsys.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 设置编码 统一为 UTF-8
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 设置请求的编码为 utf-8
        req.setCharacterEncoding("UTF-8");
        //设置响应编码 为 utf-8 ,声明为json格式
        ((HttpServletResponse) resp).setContentType("application/json;charset=UTF-8");
        ((HttpServletResponse) resp).setCharacterEncoding("UTF-8");

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
