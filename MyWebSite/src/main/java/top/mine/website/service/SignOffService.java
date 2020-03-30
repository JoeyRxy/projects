package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * SignOffService
 */
@WebServlet("/signoff")
public class SignOffService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -5197576340639799967L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        req.getSession().removeAttribute("name");
        resp.sendRedirect("login.html");
    }
}