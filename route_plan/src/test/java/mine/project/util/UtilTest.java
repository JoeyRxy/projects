package mine.project.util;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;

/**
 * Tests
 */
public class UtilTest {

    @Test
    public void unionfindTest() throws NumberFormatException, IOException {
        String fileName = "tinyUF.txt";
        InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int n = Integer.parseInt(reader.readLine());
        UnionFind uf = new UnionFind(n);
        String next = reader.readLine();
        while (next != null) {
            String[] split = next.split(" ");
            uf.union(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            next = reader.readLine();
        }
        reader.close();
        if (fileName.equals("tinyUF.txt"))
            assertTrue(uf.size() == 2);
    }
}