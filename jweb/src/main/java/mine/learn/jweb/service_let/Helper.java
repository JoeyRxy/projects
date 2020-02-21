package mine.learn.jweb.service_let;

/**
 * SignInHelper
 */
public class Helper {

    public static boolean isSignIn(javax.servlet.http.HttpServletRequest req) {
        return (boolean) req.getSession().getAttribute("isSignIn");
    }
}