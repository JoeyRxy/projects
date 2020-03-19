package mine.learn.javawebajax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import mine.learn.javawebajax.util.BCrypt;
import mine.learn.javawebajax.util.Surplus;
import mine.learn.javawebajax.util.SurplusListener;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testBCrpyt() {
        String hashed1 = BCrypt.hashpw("abc", BCrypt.gensalt());
        System.out.println(hashed1);
        String hashed2 = BCrypt.hashpw("abc", BCrypt.gensalt());
        System.out.println(hashed2);
        if (BCrypt.checkpw("abc", hashed2)) {
            System.out.println("It matches");
        } else {
            System.err.println("It doesn't match.");
        }
    }

    @Test
    public void testFile() throws IOException {
        File folder = new File("src/main/webapp/content/hhhh");
        boolean mkdir = folder.mkdir();
        File file = new File("src/main/webapp/content/hhhh/1.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("asfa;dksgfja;gagja;gja;rkgjkargj");
        writer.close();
    }

    @Test
    public void testListener() {
        Surplus surplus = new Surplus(3);
        SurplusListener listener1 = new SurplusListener() {

            @Override
            public void update(final int v) {
                System.out.println("1 have noticed surplus change to " + v);
            }
        };
        SurplusListener listener2 = new SurplusListener() {

            @Override
            public void update(int surplus) {
                System.out.println("2 have noticed surplus change to " + surplus);
            }

        };
        surplus.register(listener1);
        surplus.register(listener2);

        surplus.inc();
        surplus.dec();
    }
}
