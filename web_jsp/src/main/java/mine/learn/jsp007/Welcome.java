package mine.learn.jsp007;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.learn.dao.LogInDAO;
import mine.learn.entity.LoginInf;

/**
 * Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {

    private static final long serialVersionUID = -5404045780670523747L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(serialVersionUID);
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        try {
            LoginInf info = new LoginInf(-1, uname, upwd);
            boolean check = LogInDAO.check(info);
            if (check)
                req.getRequestDispatcher("/hello.jsp").forward(req, resp);
            else
                // resp.sendRedirect("/fucked.jsp");//
                // TODO为什么失败了？？sendRedirect找不到/failed.jsp这个资源？
                req.getRequestDispatcher("/fucked.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}