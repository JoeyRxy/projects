package mine.learn.service_let;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import mine.learn.dao.LogInDAO;
import mine.learn.entity.LoginInf;

/**
 * Welcome
 */
@WebServlet("/signin")
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = -5404045780670523747L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uname = req.getParameter("uname");
        String upwd = req.getParameter("upwd");
        try {
            LoginInf info = new LoginInf(uname, upwd, "");
            boolean check = LogInDAO.check(info);
            if (check) {
                HttpSession session = req.getSession();
                session.setAttribute("uname", uname);
                session.setAttribute("upwd", upwd);
                session.setMaxInactiveInterval(3600);
                PrintWriter writer = resp.getWriter();
                writer.write("<script>alert('登录成功！你好')</script>");

                resp.sendRedirect("index.jsp");
            } else{
                PrintWriter writer = resp.getWriter();
                writer.write("<script>alert('登录失败！重新登录')</script>");
                resp.sendRedirect("signin.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}