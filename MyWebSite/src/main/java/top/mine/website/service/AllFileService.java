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
import top.mine.website.entity.Myfile;
import top.mine.website.util.LogStateCheck;

/**
 * AllFileService
 * <p>
 * 
 */
@WebServlet("/allfile")
public class AllFileService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -7500732880338338548L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!LogStateCheck.checkLogState(req))
            return;
        String name = (String) req.getSession().getAttribute("name");
        try {
            List<Myfile> filesList = UserDAO.filesList(name);
            JSONObject json = new JSONObject();
            json.put("data", filesList);
            resp.getWriter().write(json.toJSONString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}