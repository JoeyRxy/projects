package mine.learn.trial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import mine.learn.dao.UserDAO;
import mine.learn.entity.UserInf;

/**
 * TrialServlet
 */
@WebServlet("/trial")
public class TrialServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1453568785505514681L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        // BufferedReader reader = req.getReader();
        // StringBuilder builder = new StringBuilder();
        // int c;
        // while ((c = reader.read()) != -1) {
        // builder.append((char) c);
        // }

        String name = req.getParameter("name");
        String pwd = req.getParameter("pwd");
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        try {
            if (UserDAO.check(new UserInf(name, pwd, ""))) {
                jsonObject.put("ans", true);
                writer.write(jsonObject.toString());
            } else {
                jsonObject.put("ans", false);
                writer.write(jsonObject.toString());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}