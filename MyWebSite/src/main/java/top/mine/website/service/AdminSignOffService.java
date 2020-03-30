package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AdminSignOffService
 */
@WebServlet("/adminsignoff")
public class AdminSignOffService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4804678032226797620L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        req.getSession().removeAttribute("adminname");
        resp.sendRedirect("adminlogin.html");
    }
}