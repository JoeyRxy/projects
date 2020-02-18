package mine.learn.jweb.service_let;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * SignOff
 */
@WebServlet("/signoff")
public class SignOff extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -1601346788400622135L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        session.removeAttribute("uname");

        session.setAttribute("isSignOff", true);
        resp.sendRedirect("index.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}