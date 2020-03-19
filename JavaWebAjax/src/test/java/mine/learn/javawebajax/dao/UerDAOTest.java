package mine.learn.javawebajax.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import mine.learn.javawebajax.entity.User;

/**
 * UerDAOTest
 * <p>
 * Test UserData:
 * <p>
 * 1. p1:123
 * <p>
 * 2. p2:321
 * <p>
 * 3. p3:132
 * <p>
 * 4. p4:123
 */
public class UerDAOTest {

    public void testInsert() throws SQLException {
        UserDAO.insert(new User("p1", "123", "12345678901"));
        UserDAO.insert(new User("p2", "321", "54145246256"));
        UserDAO.insert(new User("p3", "132", "67354435143"));
        UserDAO.insert(new User("p4", "123", "67365254622"));
    }

    @Test
    public void testCheck() throws SQLException {
        assertTrue(UserDAO.check(new User("p3", "132", null)));
    }

    @Test
    public void testJSONObject() {
        JSONObject json = new JSONObject();
        // json.put("key", 1);
        // json.put("hello", "world");
        // json.put("yes", true);
        JSONObject parseObject = JSONObject.parseObject(json.toJSONString());

        System.out.println(parseObject);
    }

    @Test
    public void testService() throws SQLException {
        UserDAO.appointment("p1", "day1", 1, (byte) 3);
        System.out.println(UserDAO.queryService("p1"));

    }
}