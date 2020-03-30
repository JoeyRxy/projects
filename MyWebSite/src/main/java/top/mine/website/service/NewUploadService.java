package top.mine.website.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.MyfileDAO;
import top.mine.website.dao.UserDAO;
import top.mine.website.entity.Myfile;
import top.mine.website.util.LogStateCheck;

/**
 * NewUploadService
 */
@WebServlet("/upload2")
@MultipartConfig
public class NewUploadService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 7314309888240615435L;
    private static JSONObject resource;
    static {
        InputStream resoucesInputStream = UploadService.class.getClassLoader().getResourceAsStream("upload.json");
        StringBuilder builder = new StringBuilder();
        try {
            int len;
            byte[] b = new byte[128];
            while ((len = resoucesInputStream.read(b)) != -1) {
                builder.append(new String(b, 0, len));
            }
            resoucesInputStream.close();
            resource = JSONObject.parseObject(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        if (!LogStateCheck.checkLogState(req)) {
            json.put("data", false);
            resp.getWriter().write(json.toJSONString());
            return;
        }
        Collection<Part> parts = req.getParts();
        System.out.println(parts.size());
        String username = (String) req.getSession().getAttribute("name");
        File fileRoot = new File(resource.getString("fileRoot"), username);
        fileRoot.mkdir();
        int bufSize = resource.getIntValue("threshold");
        byte[] key;
        int userId = -1;
        try {
            key = UserDAO.password(username).getBytes();
            userId = UserDAO.getUserId(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        int len;
        byte[] b = new byte[bufSize];
        for (Part part : parts) {
            String filename = part.getSubmittedFileName();
            File file = new File(fileRoot, filename);
            InputStream in = part.getInputStream();
            FileOutputStream os = new FileOutputStream(file);
            while ((len = in.read(b)) != -1) {
                for (int i = 0; i < len; i++)
                    for (int j = 0; j < key.length; j++)
                        b[i] ^= key[j];
                os.write(b, 0, len);
            }
            try {
                MyfileDAO.insert(new Myfile(userId, filename));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            in.close();
            os.close();
        }
        json.put("data", true);
        resp.getWriter().write(json.toJSONString());
    }
}