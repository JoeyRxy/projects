package mine.learn.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import mine.learn.entity.LoginInf;

/**
 * LogInTest
 */
public class LogInTest {

    @Test
    public void test() throws Exception {
        String uname = "Yy42V3";
        String upwd = "y(42V3)Ug_";
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
}