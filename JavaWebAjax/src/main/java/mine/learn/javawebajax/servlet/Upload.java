package mine.learn.javawebajax.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * Upload
 */
@WebServlet("/upload")
public class Upload extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 2418704930959559458L;
    private static JSONObject resource;
    static {
        InputStream resoucesInputStream = Upload.class.getClassLoader().getResourceAsStream("upload.json");
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
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(resource.getInteger("threshold"));
            File tmpFactory = new File(resource.getString("tempRoot"));
            factory.setRepository(tmpFactory);
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> list = upload.parseRequest(req);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        String photoName = fileItem.getFieldName();
                        String value = fileItem.getString("utf-8");
                        System.out.println(photoName + " : " + value + "\n");
                    } else {
                        InputStream in = fileItem.getInputStream();
                        String name = fileItem.getName();
                        File file = new File(resource.getString("fileRoot"), System.currentTimeMillis() + name);
                        upload.setFileSizeMax(resource.getInteger("fileSizeMax"));
                        FileOutputStream os = new FileOutputStream(file);
                        int len;
                        byte[] b = new byte[1024];
                        while ((len = in.read(b)) != -1)
                            os.write(b, 0, len);
                        os.close();
                        in.close();
                        fileItem.delete();
                    }
                }
                resp.sendRedirect("upload.html");
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }
}