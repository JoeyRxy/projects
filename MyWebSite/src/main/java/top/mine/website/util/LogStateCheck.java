package top.mine.website.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LogStateCheck
 */
public class LogStateCheck {

    public static boolean checkLogState(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("name");
        if (name == null)
            return false;
        return true;
    }

    public static void setLogIn(HttpServletRequest req, String name) {
        HttpSession session = req.getSession();
        session.setAttribute("name", name);
        session.setMaxInactiveInterval(7200);
    }
}