package mine.learn.javawebajax.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.javawebajax.dao.UserDAO;
import mine.learn.javawebajax.entity.User;
import mine.learn.javawebajax.util.StateHelper;

/**
 * Check
 */
@WebServlet("/login")
public class LogIn extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -2107943357385693391L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uname = req.getParameter("name");
        String upwd = req.getParameter("pwd");
        try {
            User info = new User(uname, upwd, "");
            boolean check = UserDAO.check(info);
            if (check) {
                resp.addCookie(new Cookie("name", uname));
                StateHelper.setLogInTrue(req, uname);
                resp.sendRedirect("index.html");
            } else {
                Cookie cookie = new Cookie("failed", "true");
                resp.addCookie(cookie);
                resp.sendRedirect("login.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}