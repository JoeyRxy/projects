package mine.learn.javawebajax.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * StateHelper
 * <p>
 * loginstate
 * <p>
 * name
 */
public class StateHelper {

    public static void setLogInTrue(HttpServletRequest req, String name) {
        HttpSession session = req.getSession();
        Boolean res = (Boolean) session.getAttribute("loginstate");
        if (res == null || res == false) {
            session.setAttribute("loginstate", true);
            session.setAttribute("name", name);
            session.setMaxInactiveInterval(3600);
        }
    }

    public static void setLogInFalse(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Boolean res = (Boolean) session.getAttribute("loginstate");
        if (res == true) {
            session.removeAttribute("loginstate");
            session.removeAttribute("name");
        }
    }

    public static boolean isLogIn(HttpServletRequest req) {
        Boolean res = (Boolean) req.getSession().getAttribute("loginstate");
        if (res == null)
            return false;
        return res;
    }
}