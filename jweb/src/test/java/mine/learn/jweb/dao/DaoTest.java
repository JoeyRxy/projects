package mine.learn.jweb.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import mine.learn.jweb.entity.User;

/**
 * DaoTest
 */
public class DaoTest {

    @Test
    public void insert() throws SQLException {
        boolean insert = UserDAO.insert(new User("uname", "upwd", "test"));
        assertTrue("failed", insert);

    }
}