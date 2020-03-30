package top.mine.website.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.mine.website.dao.UserDAO;
import top.mine.website.entity.User;
import top.mine.website.util.AdminStateCheck;

/**
 * AliasChangeService
 */
@WebServlet("/aliaschange")
public class AliasChangeService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6522549981001041888L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String alias = req.getParameter("alias");
        if (!AdminStateCheck.checkLogState(req) || name == null || UserDAO.getAlias(name).equals(alias))
            resp.getWriter().write("{\"data\":false}");
        try {
            UserDAO.aliasChange(new User(name, alias, null));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("{\"data\":false}");
        }
        resp.getWriter().write("{\"data\":true}");
    }
}