package mine.learn.jsp007;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.dao.LogInDAO;
import mine.learn.entity.LoginInf;

/**
 * Welcome
 */
@WebServlet("/signin")
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = -5404045780670523747L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        try {
            LoginInf info = new LoginInf(uname, upwd, "");
            boolean check = LogInDAO.check(info);
            if (check){
                Cookie cookie = new Cookie("uname",uname);
                resp.sendRedirect("/index.html");
            }
            else
                req.getRequestDispatcher("/fucked.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}