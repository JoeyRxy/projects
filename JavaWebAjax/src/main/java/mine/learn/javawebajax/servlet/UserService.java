package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.javawebajax.dao.UserDAO;
import mine.learn.javawebajax.util.StateHelper;

/**
 * UserService
 */
@WebServlet("/userservice")
public class UserService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -7987885383898576852L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            String name = (String) req.getSession().getAttribute("name");
            try {
                JSONObject json = UserDAO.queryService(name);
                if (json != null)
                    resp.getWriter().write(json.toJSONString());
                else {
                    JSONObject object = new JSONObject();
                    object.put("not", false);
                    resp.getWriter().write(object.toJSONString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}