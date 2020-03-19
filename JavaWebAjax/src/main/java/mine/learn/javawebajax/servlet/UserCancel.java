package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.javawebajax.dao.ScheduleDAO;
import mine.learn.javawebajax.dao.UserDAO;
import mine.learn.javawebajax.util.StateHelper;

/**
 * UserCancel
 */
@WebServlet("/usercancel")
public class UserCancel extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 4754064406302061811L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            String name = (String) req.getSession().getAttribute("name");
            String table = req.getParameter("table");
            int doc_id = Integer.parseInt(req.getParameter("doc_id"));
            byte timei = Byte.parseByte(req.getParameter("timei"));
            try {
                UserDAO.cancel(name);
                int inc = ScheduleDAO.increase(table, doc_id, timei);
                JSONObject json = new JSONObject();
                json.put("count", inc);
                resp.getWriter().write(json.toJSONString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}