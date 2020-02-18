package mine.learn.jweb.service_let;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

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
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        try {
            User info = new User(uname, upwd, "");
            boolean check = UserDAO.check(info);
            if (check) {
                HttpSession session = req.getSession();
                Iterator<String> iter = session.getAttributeNames().asIterator();
                System.out.println("====== sessions =======");
                while (iter.hasNext()) {
                    String next = iter.next();
                    System.out.println(next + " : " + session.getAttribute(next));
                }
                resp.addCookie(new Cookie("name", uname));
                resp.addCookie(new Cookie("pwd", upwd));
                System.out.println("========== cookies ============");
                Cookie[] cookies = req.getCookies();
                for (Cookie cookie : cookies) {
                    System.out.println(cookie.getName() + ": " + cookie.getValue());
                }
                resp.sendRedirect("space.html");
            } else {
                Cookie cookie = new Cookie("failed", "true");
                resp.addCookie(cookie);
                resp.sendRedirect("signin.html");
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