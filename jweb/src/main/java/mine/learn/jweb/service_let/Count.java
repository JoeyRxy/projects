package mine.learn.jweb.service_let;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.jweb.dao.UserDAO;

@WebServlet("/count")
public class Count extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 7735318263832033385L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int count = UserDAO.queryCount();
            req.setAttribute("count", count);
            req.getRequestDispatcher("/trial.html").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
