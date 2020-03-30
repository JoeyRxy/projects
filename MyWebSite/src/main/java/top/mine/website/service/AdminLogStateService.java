package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.util.AdminStateCheck;

/**
 * AdminLogStateService
 */
@WebServlet("/adminloginstate")
public class AdminLogStateService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1194346099537415380L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONObject data = new JSONObject();
        if (AdminStateCheck.checkLogState(req)) {
            data.put("data", true);
        } else {
            data.put("data", false);
        }
        resp.getWriter().write(data.toJSONString());
    }
}