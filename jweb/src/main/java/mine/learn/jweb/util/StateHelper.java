package mine.learn.jweb.util;

import javax.servlet.http.HttpServletRequest;

/**
 * StateHelper
 */
public class StateHelper {

    public static void setLogInTrue(HttpServletRequest req) {
        if (!isLogIn(req))
            req.getSession().setAttribute("signin", true);
    }

    public static boolean isLogIn(HttpServletRequest req) {
        var res = (Boolean) req.getSession().getAttribute("signin");
        if (res == null)
            return false;
        return res;
    }
}