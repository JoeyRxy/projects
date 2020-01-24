package mine.learn.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import mine.learn.entity.LoginInf;

/**
 * LogInTest
 */
public class LogInTest {

    @Test
    public void test() throws Exception {
        String uname = "rxy";
        String upwd = "123";
        LoginInf info = new LoginInf(uname, upwd, "");
        boolean check = LogInDAO.check(info);
        assertTrue(check);
        System.out.println(uname);
    }

    @Test
    public void testSignUp() throws Exception {
        String uname = "任星宇";
        String upwd = "73699rxy";
        String umobile = "15652580228";
        LoginInf info = new LoginInf(uname, upwd, umobile);
        boolean insert = LogInDAO.insert(info);
        assertTrue(insert);
    }

    @Test
    public void queryAllTest() throws SQLException {
        List<LoginInf> all = LogInDAO.queryAll();
        for (LoginInf loginInf : all) {
            System.out.println(loginInf);
        }
    }

    @Test
    public void testDelete() throws SQLException {
        String uname = "rxy";
        LogInDAO.delete(uname);
        assertTrue("delete failed!!\n", !LogInDAO.is(uname));
    }
}