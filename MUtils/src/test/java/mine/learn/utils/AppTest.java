package mine.learn.utils;

import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.regex.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     * 
     * @throws IOException
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        File file = new File("tests.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        int c = 0;
        while ((c = bufferedReader.read()) != -1) {
            buffer.append((char) c);
        }
        String input = new String(buffer);
        System.out.print(input);
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher(input);
        // assertTrue(matcher.find());
        if (matcher.find())
            System.out.println("start:" + matcher.start());
        input = matcher.replaceAll("def");
        System.out.print(input);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(input);

        bufferedReader.close();
        writer.close();

    }
}
