package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author WL
 * @Date 2020-10-21 10:09
 * @Version 1.0
 */

public class CookieUtil {

    //1。新增cookie
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int seconds, String doMain) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setDomain(doMain);
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);

    }
    //2。根据name查value的值

    public static String getValue(HttpServletRequest request, String cookieName) {
        // 获取cookie
        Cookie[] cookies = request.getCookies();
        //  判断cookie是否存在
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;

    }


    //3.删除cookie

    /**
     * @param response
     * @param cookieName 名字
     * @param doMain     共享
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName, String doMain) {
        // 新增cookie 的复用
        addCookie(response, cookieName, "", 0, doMain);


    }


}
