package top.mine.website.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.MyfileDAO;
import top.mine.website.dao.UserDAO;
import top.mine.website.entity.Myfile;

/**
 * DeleteFileService
 */
@WebServlet("/delete")
public class DeleteFileService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 2654083468797939836L;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String filename = req.getParameter("filename");
        String name = (String) req.getSession().getAttribute("name");
        File file = new File(fileRoot + "/" + name, filename);
        boolean delete = file.delete();

        try {
            MyfileDAO.delete(new Myfile(UserDAO.getUserId(name), filename));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject res = new JSONObject();
        res.put("data", delete);
        resp.getWriter().write(res.toJSONString());
    }
}