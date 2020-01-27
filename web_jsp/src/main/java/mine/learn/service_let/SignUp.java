package mine.learn.service_let;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mine.learn.dao.UserDAO;
import mine.learn.entity.UserInf;

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
            UserInf info = new UserInf(uname, upwd, umobile);
            boolean success = UserDAO.insert(info);
            if (success) {
                resp.sendRedirect("signin.jsp");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("fucked", true);
                resp.sendRedirect("signup.jsp");
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