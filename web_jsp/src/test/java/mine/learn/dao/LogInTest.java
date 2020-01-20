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
        LoginInf info = new LoginInf(-1, uname, upwd);
        boolean check = LogInDAO.check(info);
        assertTrue(check);
        if (check)
            System.out.println(uname);
    }
}