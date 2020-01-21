package mine.learn.jsp007;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.dao.LogInDAO;
import mine.learn.entity.LoginInf;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        String umobile = req.getParameter("umobile");

        try {
            LoginInf info = new LoginInf(uname, upwd, umobile);
            boolean success = LogInDAO.insert(info);
            if (success) {
                req.getRequestDispatcher("/index.html").forward(req, resp);
            } else {
                resp.sendRedirect("/fucked.jsp");
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