package mine.learn.service_let;

import mine.learn.dao.LogInDAO;
import mine.learn.entity.LoginInf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/all")
public class All extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4378791261535224101L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<LoginInf> users = LogInDAO.queryAll();
            req.setAttribute("users", users);

            req.getRequestDispatcher("trial.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
