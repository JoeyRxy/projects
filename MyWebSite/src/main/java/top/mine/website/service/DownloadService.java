package top.mine.website.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.UserDAO;

/**
 * DownloadService
 */
@WebServlet("/download")
public class DownloadService extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4522186086147489007L;
    private static String fileRoot;
    private static int bufSize;

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
            bufSize = data.getInteger("threshold");
            data = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String filename = req.getParameter("filename");
        resp.setContentType("application/x-msdownload");
        resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        String userName = (String) req.getSession().getAttribute("name");
        File file = new File(fileRoot + "/" + userName, filename);
        resp.addHeader("Content-Length", "" + file.length());
        ServletOutputStream os = resp.getOutputStream();
        InputStream in = new FileInputStream(file);
        try {
            byte[] key = UserDAO.password(userName).substring(7, 10).getBytes();
            byte[] b = new byte[bufSize];
            int len;
            while ((len = in.read(b)) != -1) {
                for (int i = 0; i < len; i++)
                    for (int j = 0; j < key.length; j++)
                        b[i] ^= key[j];
                os.write(b, 0, len);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        os.close();
        in.close();
    }
}