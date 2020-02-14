package mine.learn;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;

import mine.learn.dao.UserDAO;
import mine.learn.entity.UserInf;

/**
 * CreateRamdomDB
 */
public class CreateRamdomDB {

    Random r = new Random(System.currentTimeMillis());

    private char randomChar() {
        int c = r.nextInt(26);
        return ((r.nextBoolean()) ? ((char) (c + 65)) : ((char) (c + 97)));
    }

    public String randomString(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(randomChar());
        }
        return builder.toString();
    }

    public String randomMobileString(int len) {
        StringBuilder builder = new StringBuilder();
        builder.append('1');
        for (int i = 0; i < len - 1; i++) {
            builder.append((char) (48 + r.nextInt(10)));
        }
        return builder.toString();
    }

    // @Test
    // public void createDB() throws InterruptedException, SQLException {
    // CreateRamdomDB t = new CreateRamdomDB();
    // for (int i = 0; i < 1000; i++) {
    // UserInf info = new UserInf(t.randomString(6), t.randomString(12),
    // t.randomMobileString(11));
    // UserDAO.insert(info);
    // }
    // }

    @Test
    public void nothing() {
        StringBuilder builder = new StringBuilder();
        builder.append((char) 69);
        String s = new String(builder);
        System.out.println(s);
        System.out.println(builder);
    }

    public void create() throws SQLException {
        CreateRamdomDB db = new CreateRamdomDB();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20000; i++) {
            UserDAO.insert(
                    new UserInf(db.randomString(r.nextInt(20) + 4), db.randomString(10), db.randomMobileString(11)));
        }
    }
}