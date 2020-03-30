package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.util.LogStateCheck;

/**
 * LogStateService
 */
@WebServlet("/loginstate")
public class LogStateService extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -559066518363794541L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if (!LogStateCheck.checkLogState(req)) {
            json.put("data", false);
        } else {
            json.put("data", true);
        }
        resp.getWriter().write(json.toJSONString());
    }
}