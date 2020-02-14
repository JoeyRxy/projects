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
        resp.setContentType("tex/html; charset=utf-8");
        // BufferedReader reader = req.getReader();
        // StringBuilder builder = new StringBuilder();
        // int c;
        // while ((c = reader.read()) != -1) {
        // builder.append((char) c);
        // }

        String name = req.getParameter("name");
        String pwd = req.getParameter("pwd");
        PrintWriter writer = resp.getWriter();

        try {
            JSONObject object = new JSONObject();
            List<UserInf> allList = UserDAO.queryAll();
            UserInf user1 = allList.get(100);
            UserInf user2 = allList.get(102);
            UserInf user3 = allList.get(104);
            UserInf user4 = allList.get(1);
            UserInf user5 = allList.get(10);
            object.put("user1", user1);
            object.put("user2", user2);
            object.put("user3", user3);
            object.put("user4", user4);
            object.put("user5", user5);
            writer.write(object.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}