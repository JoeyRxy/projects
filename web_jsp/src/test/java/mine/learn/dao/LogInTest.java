package mine.learn.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import mine.learn.entity.UserInf;

/**
 * LogInTest
 */
public class LogInTest {

    @Test
    public void test() throws Exception {
        String uname = "rxy";
        String upwd = "123";
        UserInf info = new UserInf(uname, upwd, "");
        boolean check = UserDAO.check(info);
        assertTrue(check);
        System.out.println(uname);
    }

    @Test
    public void testSignUp() throws Exception {
        String uname = "任星宇";
        String upwd = "73699rxy";
        String umobile = "15652580228";
        UserInf info = new UserInf(uname, upwd, umobile);
        boolean insert = UserDAO.insert(info);
        assertTrue(insert);
    }

    @Test
    public void queryAllTest() throws SQLException {
        List<UserInf> all = UserDAO.queryAll();
        for (UserInf userInf : all) {
            System.out.println(userInf);
        }
    }

    @Test
    public void testDelete() throws SQLException {
        String uname = "rxy";
        UserDAO.delete(uname);
        assertTrue("delete failed!!\n", !UserDAO.is(uname));
    }

    @Test
    public void testCount() throws SQLException {
        System.out.println(UserDAO.queryCount());
    }
}