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
@WebServlet("/signup")
public class SignUp extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6453855645464399920L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        String umobile = req.getParameter("umobile");

        try {
            User info = new User(uname, upwd, umobile);
            boolean success = UserDAO.insert(info);
            if (success) {
                resp.addCookie(new Cookie("name", uname));
                resp.addCookie(new Cookie("pwd", upwd));
                resp.sendRedirect("signin.html");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("fucked", true);
                resp.sendRedirect("signup.html");
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