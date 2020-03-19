package mine.learn.javawebajax.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

/**
 * LogInState
 */
@WebServlet("/loginstate")
public class LogInState extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4278592500798779477L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean state = (boolean) session.getAttribute("loginstate");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginstate", state);
        resp.getWriter().write(jsonObject.toJSONString());
    }
}