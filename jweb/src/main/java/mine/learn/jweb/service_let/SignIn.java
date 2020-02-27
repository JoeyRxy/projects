package mine.learn.jweb.service_let;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.jweb.dao.UserDAO;
import mine.learn.jweb.entity.User;
import mine.learn.jweb.util.StateHelper;

/**
 * Welcome
 */
@WebServlet("/signin")
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = -5404045780670523747L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            resp.getWriter().write("您已经登录");
            resp.sendRedirect("index.html");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        try {
            User info = new User(uname, upwd, "");
            boolean check = UserDAO.check(info);
            if (check) {
                resp.addCookie(new Cookie("name", uname));
                resp.addCookie(new Cookie("pwd", upwd));
                StateHelper.setLogInTrue(req);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}