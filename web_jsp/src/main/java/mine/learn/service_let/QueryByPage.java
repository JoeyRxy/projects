package mine.learn.service_let;

import mine.learn.dao.UserDAO;
import mine.learn.entity.Page;
import mine.learn.entity.UserInf;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * QueryByPage
 */
@WebServlet("/page")
public class QueryByPage extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5017528247924748132L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            int cPage = -1;
            String curPage = req.getParameter("curPage");
            if (curPage == null)
                curPage = "0";
            cPage = Integer.parseInt(curPage);

            String size = req.getParameter("pageSize");
            if (size == null)
                size = "10";
            int pageSize = Integer.parseInt(size);
            List<UserInf> users = UserDAO.queryByPage(cPage, pageSize);
            Page p = new Page(cPage, pageSize, users);
            req.setAttribute("p", p);
            req.getRequestDispatcher("/trial.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}