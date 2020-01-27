package mine.learn.service_let;

import mine.learn.dao.UserDAO;
import mine.learn.entity.UserInf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/all")
public class GetAll extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4378791261535224101L;

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            final List<UserInf> users = UserDAO.queryAll();
            req.setAttribute("users", users);

            req.getRequestDispatcher("trial.jsp").forward(req, resp);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
