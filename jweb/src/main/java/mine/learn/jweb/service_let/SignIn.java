package mine.learn.jweb.service_let;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mine.learn.jweb.dao.UserDAO;
import mine.learn.jweb.entity.User;

/**
 * Welcome
 */
@WebServlet("/signin")
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = -5404045780670523747L;

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
                resp.addCookie(new Cookie("pwd", upwd));
                HttpSession session = req.getSession();
                session.setAttribute("signin", true);
                session.setMaxInactiveInterval(3600);
                resp.sendRedirect("space.html");
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