package mine.learn.javawebajax.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.javawebajax.dao.DoctorDAO;
import mine.learn.javawebajax.entity.Doctor;
import mine.learn.javawebajax.util.StateHelper;

/**
 * DoctorInfo
 */
@WebServlet("/alldoc")
public class AllDoctors extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4590750873886272326L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StateHelper.isLogIn(req)) {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            try {
                List<Doctor> all = DoctorDAO.all();
                JSONObject jsonObject = new JSONObject();
                for (Doctor doctor : all) {
                    jsonObject.put(doctor.getName(), doctor.getAbout());
                }
                resp.getWriter().write(jsonObject.toJSONString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}