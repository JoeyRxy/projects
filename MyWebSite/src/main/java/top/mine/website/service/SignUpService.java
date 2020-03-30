package top.mine.website.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;
import top.mine.website.entity.User;
import top.mine.website.util.LogStateCheck;

/**
 * SignUpService
 */
@WebServlet("/signup")
public class SignUpService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -9158209752055791316L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (LogStateCheck.checkLogState(req))
            return;
        String name = req.getParameter("name");
        String alias = req.getParameter("alias");
        String pwd = req.getParameter("pwd");
        User user = new User(name, alias, pwd);
        JSONObject json = new JSONObject();
        try {
            if (UserDAO.getUserId(name) != -1) {
                json.put("data", false);
                resp.getWriter().write(json.toJSONString());
            } else {
                UserDAO.insert(user);
                json.put("data", true);
                resp.getWriter().write(json.toJSONString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}