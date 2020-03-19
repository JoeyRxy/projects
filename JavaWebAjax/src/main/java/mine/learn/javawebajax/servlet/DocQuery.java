package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.javawebajax.dao.ScheduleDAO;
import mine.learn.javawebajax.util.StateHelper;

/**
 * DocQuery
 */
@WebServlet("/querydoc")
public class DocQuery extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1629420878567039909L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            resp.setCharacterEncoding("utf-8");
            String table = req.getParameter("table");
            int doc_id = Integer.parseInt(req.getParameter("doc_id"));
            String timei = req.getParameter("timei");
            try {
                int res = ScheduleDAO.query(table, doc_id, Byte.parseByte(timei));
                resp.getWriter().write(res);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}