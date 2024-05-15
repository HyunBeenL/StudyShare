package org.fullstack4.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CommonUtil {
    public static boolean loginCheck(HttpSession session) {
        return session.getAttribute("user_id") != null;
    }

    public static boolean isValue(String str) {
        return str!=null && !str.isEmpty() && !str.isBlank() ;
    }
    public static boolean SaveIdcheck(HttpServletRequest req) {
        return CookieUtil.getCookieValue(req, "user_id") != "";
    }
}
