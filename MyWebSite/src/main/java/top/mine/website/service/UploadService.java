package top.mine.website.service;

import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import top.mine.website.dao.MyfileDAO;
import top.mine.website.dao.UserDAO;
import top.mine.website.entity.Myfile;
import top.mine.website.util.LogStateCheck;

/**
 * UploadService
 */
@WebServlet("/upload")
public class UploadService extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 2418704930959559458L;
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
        if (!LogStateCheck.checkLogState(req))
            return;
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(resource.getInteger("threshold"));
            File tmpFactory = new File(resource.getString("tempRoot"));
            factory.setRepository(tmpFactory);
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> list = upload.parseRequest(req);
                String filename = null;
                String fieldname = null;
                InputStream in = null;
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        filename = fileItem.getString("utf-8");
                    } else {
                        in = fileItem.getInputStream();
                        fieldname = fileItem.getName();
                    }
                }
                String userName = (String) req.getSession().getAttribute("name");
                if (userName == null) {
                    resp.sendRedirect("login.html");
                    throw new NullPointerException("名字不存在，请重新登录！");
                }
                if (filename == null)
                    filename = fieldname;
                File fileRoot = new File(resource.getString("fileRoot") + "/" + userName);
                fileRoot.mkdir();
                File file = new File(fileRoot, filename);
                upload.setFileSizeMax(resource.getInteger("fileSizeMax"));
                FileOutputStream os = new FileOutputStream(file);
                byte[] key = UserDAO.password(userName).substring(7, 10).getBytes();
                int len;
                byte[] b = new byte[10240];
                while ((len = in.read(b)) != -1) {
                    for (int i = 0; i < len; i++)
                        for (int j = 0; j < key.length; j++)
                            b[i] ^= key[j];
                    os.write(b, 0, len);
                }
                os.close();
                in.close();
                MyfileDAO.insert(new Myfile(UserDAO.getUserId(userName), filename, file.length()));
                // resp.sendRedirect("files.html");
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}