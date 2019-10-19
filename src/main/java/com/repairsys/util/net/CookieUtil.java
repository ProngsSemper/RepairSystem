package com.repairsys.util.net;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Prongs
 * @date 2019/10/16 20:09
 * cookie工具类
 * 直接从cookie拿id进行查询
 */
public class CookieUtil {
    public static String getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
