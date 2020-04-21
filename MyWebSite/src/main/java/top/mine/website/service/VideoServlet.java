package top.mine.website.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;
import top.mine.website.util.LogStateCheck;

@WebServlet("/video")
public class VideoServlet extends HttpServlet {
    private static String fileRoot;
    private static String tmpRoot;

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
            JSONObject data = JSONObject.parseObject(builder.toString());
            fileRoot = data.getString("fileRoot");
            tmpRoot = data.getString("tempRoot");
            data = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private static final long serialVersionUID = 6960880505979547309L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (!LogStateCheck.checkLogState(req))
            return;
        String userName = (String) req.getSession().getAttribute("name");
        byte[] key;
        try {
            key = UserDAO.password(userName).substring(7, 10).getBytes();
            byte[] b = new byte[10240000];
            String videoname = req.getParameter("videoname");
            FileInputStream in = new FileInputStream(new File(fileRoot + "/" + userName, videoname));
            File tempdir = new File(tmpRoot, userName);
            if (!tempdir.exists())
                tempdir.mkdir();
            FileOutputStream os = new FileOutputStream(new File(tempdir, videoname));
            int len;
            while ((len = in.read(b)) != -1) {
                for (int i = 0; i < len; i++)
                    for (int j = 0; j < key.length; j++)
                        b[i] ^= key[j];
                os.write(b, 0, len);
            }
            in.close();
            os.close();
            resp.getWriter().write("webvideo/" + userName + "/" + videoname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}