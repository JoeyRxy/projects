package mine.learn.jweb.service_let;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.jweb.dao.UserDAO;

@WebServlet("/delete")
public class DeleteInf extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 6123632653725611629L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uname = req.getParameter("uname");

        try {
            UserDAO.delete(uname);
            // String contextPath = req.getContextPath();
            // List<LoginInf> loginInfs = LogInDAO.queryAll();
            // req.setAttribute("loginfos",loginInfs);
            // req.getRequestDispatcher(contextPath).forward(req,resp);
            resp.sendRedirect("all");
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
