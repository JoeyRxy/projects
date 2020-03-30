package top.mine.website.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.util.LogStateCheck;

/**
 * MultiThreadUpload
 */
@WebServlet("/multiupload")
public class MultiThreadUpload extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -6768125504538442401L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if (!LogStateCheck.checkLogState(req)) {
            json.put("data", false);
            resp.getWriter().write(json.toJSONString());
            return;
        }
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            InputStream in = part.getInputStream();
            // part.
        }
    }
}