package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;
import top.mine.website.util.LogStateCheck;

/**
 * AliasService
 */
@WebServlet("/alias")
public class AliasService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 3923330582649572670L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!LogStateCheck.checkLogState(req))
            return;
        JSONObject data = new JSONObject();
        data.put("data", UserDAO.getAlias((String) req.getSession().getAttribute("name")));
        System.out.println(data.toJSONString());
        resp.getWriter().write(data.toJSONString());
    }
}