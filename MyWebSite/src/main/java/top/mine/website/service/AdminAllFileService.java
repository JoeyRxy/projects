package top.mine.website.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;
import top.mine.website.util.AdminStateCheck;

/**
 * AdminAllFileService
 */
@WebServlet("/adminallfile")
public class AdminAllFileService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -6025517934665163227L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!AdminStateCheck.checkLogState(req))
            return;
        String username = req.getParameter("name");
        try {
            List<String> filesList = UserDAO.filesList(username);
            JSONObject json = new JSONObject();
            json.put("data", filesList);
            resp.getWriter().write(json.toJSONString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}