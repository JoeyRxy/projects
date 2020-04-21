package top.mine.website.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.util.LogStateCheck;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7279553298396851025L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!LogStateCheck.checkLogState(req))
            return;
        String name = (String) req.getSession().getAttribute("name");
        String filename = req.getParameter("filename");
        resp.getWriter().write("localfiles/" + name + "/" + filename);
    }
}