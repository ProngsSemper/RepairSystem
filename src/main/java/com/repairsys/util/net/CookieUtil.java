package com.repairsys.util.net;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Prongs
 * @date 2019/10/16 20:09
 * cookie工具类
 * 直接从cookie拿id进行查询
 */
public final class CookieUtil {
    public static String getCookie(String name, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {

            if (name.equals(cookie.getName())) {
                System.out.println(cookie.getValue());
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置cookie
     *
     * @param cookieName cookie的名字
     * @param cookie     cookie值
     * @param response   响应
     */
    public static void setCookie(String cookieName, String cookie, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("utf-8");
        Cookie ck = new Cookie(cookieName, URLEncoder.encode(cookie, "utf-8"));
        ck.setPath("/");
        ck.setMaxAge(60 * 60);
        response.addCookie(ck);
    }

    public static void setToken(String cookieName, String cookie, HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie ck = new Cookie(cookieName, URLEncoder.encode(cookie, "utf-8"));
        ck.setPath("/");
        ck.setMaxAge(5 * 365 * 24 * 60 * 60);
        response.setCharacterEncoding("utf-8");
        response.addCookie(ck);
    }

    /**
     * 清空cookie的方法
     */
    public static void cleanCookie(String cookieName, String cookie, HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie ck = new Cookie(cookieName, URLEncoder.encode(cookie, "utf-8"));
        ck.setPath("/");
        ck.setMaxAge(0);
        response.addCookie(ck);
    }

}
