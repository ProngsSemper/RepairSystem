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
public class CookieUtil {
    public static String getCookie(String name, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return "";
        }
        // System.out.println("fuck you2");
        for (Cookie cookie : cookies) {
            // System.out.println("fuck you3");
            if (name.equals(cookie.getName())) {
                // System.out.println("fuck you 4");
                return cookie.getValue();
            }
        }
        // System.out.println("fuck you5");
        return "";
    }

    /**
     * 设置cookie
     *
     * @param cookieName cookie的名字
     * @param cookie     cookie值
     * @param response   响应
     */
    public static void setCookie(String cookieName, String cookie, HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie ck = new Cookie(cookieName, URLEncoder.encode(cookie,"utf-8"));

        ck.setPath("/");
        ck.setMaxAge(60 * 60);

        response.addCookie(ck);
        // System.out.println("fuck ");
    }
}
