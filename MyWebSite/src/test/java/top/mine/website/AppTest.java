package top.mine.website;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import top.mine.website.dao.MyfileDAO;
import top.mine.website.dao.UserDAO;
import top.mine.website.entity.Myfile;
import top.mine.website.entity.User;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testUserDAOInsert() {
        UserDAO.insert(new User("zs", "sssdfa", "73699rxy"));
    }

    @Test
    public void testAll() {
        List<User> all = UserDAO.all();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void testCheck() {
        boolean check = UserDAO.check(new User("zs", null, "73699rxy"));
        System.out.println(check);
    }

    @Test
    public void testFileDAO() throws SQLException {
        MyfileDAO.insert(new Myfile(UserDAO.getUserId("rxy"), "lkjnbgar"));
        MyfileDAO.insert(new Myfile(UserDAO.getUserId("zs"), "thytrsh"));
    }

    @Test
    public void testListFile() throws SQLException {
        List<String> list = UserDAO.filesList("zs");
        for (String filename : list) {
            System.out.println(filename);
        }
    }

    @Test
    public void testAlias() {
        String alias = UserDAO.getAlias("rxy");
        System.out.println(alias);
    }

    @Test
    public void testAliasChange() throws SQLException {
        UserDAO.aliasChange(new User("rxy", "autgljgalkgakrga", null));
    }

    @Test
    public void testDelete() {
        UserDAO.delete("fsdf");
    }

    @Test
    public void testJSON() {
        JSONObject object = new JSONObject();
        object.put("data", false);
        String jsonString = object.toJSONString();
        System.out.println(jsonString);
        String json2 = "{\"data\":false}";
        System.out.println(json2);
        JSONObject parseObject = JSONObject.parseObject(json2);
        System.out.println(parseObject);
    }

    @Test
    public void testMultiThreadRead() {
        File file = new File("src/main/webapp/files.html");
        for (int i = 0; i < 3; i++) {
            new Thread(new ReadFile(file)).start();
        }
    }

    class ReadFile implements Runnable {

        private File file;

        ReadFile(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try {
                FileInputStream in = new FileInputStream(file);
                int len;
                byte[] b = new byte[10];
                StringBuilder builder = new StringBuilder();
                while ((len = in.read(b)) != -1) {
                    builder.append(new String(b, 0, len));
                }
                in.close();
                System.out.println(Thread.currentThread().getName() + " : " + builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
