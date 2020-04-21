package top.mine.website.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AdminStateCheck
 */
public class AdminStateCheck {

    public static boolean checkLogState(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("adminname");
        if (name == null)
            return false;
        return true;
    }

    public static void setLogIn(HttpServletRequest req, String name) {
        HttpSession session = req.getSession();
        session.setAttribute("adminname", name);
        session.setMaxInactiveInterval(900);
    }
}