package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.javawebajax.dao.ScheduleDAO;
import mine.learn.javawebajax.entity.DocSchedule;
import mine.learn.javawebajax.util.StateHelper;

/**
 * DayServlet
 */
@WebServlet("/dayall")
public class DayAll extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -5594437847293605222L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            String table = req.getParameter("table");
            try {
                List<DocSchedule> all = ScheduleDAO.all(table);
                JSONObject json = new JSONObject();
                json.put("ans", all);
                resp.getWriter().write(json.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}