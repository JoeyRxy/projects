package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.mine.website.dao.UserDAO;
import top.mine.website.entity.User;
import top.mine.website.util.LogStateCheck;

/**
 * LogInService
 */
@WebServlet("/login")
public class LogInService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7222920486315625406L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        if (UserDAO.check(new User(name, null, req.getParameter("pwd")))) {
            LogStateCheck.setLogIn(req, req.getParameter("name"));
            resp.addCookie(new Cookie("name", UserDAO.getAlias(name)));
            resp.sendRedirect("index.html");
        } else {
            Cookie cookie = new Cookie("failed", "true");
            resp.addCookie(cookie);
            resp.sendRedirect("login.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("get : hello");
    }
}