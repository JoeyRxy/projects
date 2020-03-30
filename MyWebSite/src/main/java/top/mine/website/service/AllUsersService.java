package top.mine.website.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;
import top.mine.website.entity.User;
import top.mine.website.util.AdminStateCheck;

/**
 * AllUsersService
 */
@WebServlet("/allusers")
public class AllUsersService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1308532657016172461L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!AdminStateCheck.checkLogState(req))
            return;
        List<User> all = UserDAO.all();
        JSONObject data = new JSONObject();
        data.put("data", all);
        resp.getWriter().write(data.toJSONString());
    }
}