package top.mine.website.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.MyfileDAO;
import top.mine.website.dao.UserDAO;
import top.mine.website.entity.Myfile;
import top.mine.website.util.AdminStateCheck;

/**
 * DeleteUserService
 */
@WebServlet("/deleteuser")
public class DeleteUserService extends HttpServlet {

    private static String fileRoot;

    static {
        InputStream resoucesInputStream = UploadService.class.getClassLoader().getResourceAsStream("upload.json");
        StringBuilder builder = new StringBuilder();
        try {
            int len;
            byte[] b = new byte[1024];
            while ((len = resoucesInputStream.read(b)) != -1) {
                builder.append(new String(b, 0, len));
            }
            resoucesInputStream.close();
            fileRoot = JSONObject.parseObject(builder.toString()).getString("fileRoot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private static final long serialVersionUID = 337528227116551427L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AdminStateCheck.checkLogState(req))
            return;
        String name = req.getParameter("name");
        boolean delete = UserDAO.delete(name);
        if (!delete) {
            try {
                List<Myfile> filesList = UserDAO.filesList(name);
                for (Myfile file : filesList) {
                    MyfileDAO.delete(file);
                    File _file = new File(fileRoot + "/" + name, file.getFileName());
                    _file.delete();
                }
                UserDAO.delete(name);
                resp.getWriter().write("{\"data\":" + true + "}");
            } catch (SQLException e) {
                e.printStackTrace();
                resp.getWriter().write("{\"data\":" + false + "}");
            }
        }
    }
}