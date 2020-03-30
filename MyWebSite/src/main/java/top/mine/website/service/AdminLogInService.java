package top.mine.website.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.AdminDAO;
import top.mine.website.util.AdminStateCheck;

/**
 * AdminLogInService
 */
@WebServlet("/adminlogin")
public class AdminLogInService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 8631019401028125012L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (AdminStateCheck.checkLogState(req)) {
            resp.sendRedirect("admin.html");
            ;
        }
        String adminname = req.getParameter("adminname");
        String adminpwd = req.getParameter("adminpwd");
        try {
            JSONObject data = new JSONObject();
            if (AdminDAO.check(adminname, adminpwd)) {
                data.put("data", true);
                AdminStateCheck.setLogIn(req, adminname);
            } else
                data.put("data", false);
            resp.getWriter().write(data.toJSONString());
        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("{\"data\":false}");
        }
    }
}