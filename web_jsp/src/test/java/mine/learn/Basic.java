package mine.learn;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import mine.learn.entity.UserInf;

/**
 * Basic
 */
public class Basic {

    @Test
    public void path() {
        final String path1 = getClass().getResource("").getPath();
        final String path2 = getClass().getResource("/").getPath();
        final String path3 = getClass().getClassLoader().getResource("").getPath();
        // String path4 = getClass().getClassLoader().getResource("/").getPath();
        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);
        // System.out.println(path4);
    }

    @Test
    public void testMap() {
        UserInf info = new UserInf("uname", "upwd", "umobile");
        HashSet<UserInf> set = new HashSet<>();
        set.add(info);
        assertTrue("Fucked", set.contains(new UserInf("uname", "upwd", "umobile")));
    }
}