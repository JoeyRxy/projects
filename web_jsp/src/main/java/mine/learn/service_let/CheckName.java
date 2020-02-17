package mine.learn.service_let;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.dao.UserDAO;

/**
 * CheckName
 */
@WebServlet("/name")
public class CheckName extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1077406208252189793L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        JSONObject object = new JSONObject();
        try {
            if (UserDAO.is(name))
                object.put("ans", false);
            else
                object.put("ans", true);
            writer.write(object.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}