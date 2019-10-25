package mine.learn.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CssProcessor
 */
public class CssProcessor {

    public static void main(String[] args) throws IOException {
        File file = new File("C:/Program Files/VSCode/resources/app/out/vs/workbench/workbench.desktop.main.css");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        int c;
        while ((c = bufferedReader.read()) != -1) {
            buffer.append((char) c);
        }
        String txt = new String(buffer);
        System.out.print(txt);
        String prefix = "font-size:";
        String regex;
        int font_size = 16;
        while (font_size >= 9) {
            regex = prefix + font_size + "px";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(txt);
            System.out.println(matcher);
            txt = matcher.replaceAll(prefix + (font_size + 2) + "px");

            font_size--;
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        bufferedWriter.write(txt);

        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();
        file = new File("C:/Program Files/VSCode/resources/app/out/vs/workbench/workbench.desktop.main.nls.js");
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        buffer = new StringBuffer();
        while ((c = bufferedReader.read()) != -1) {
            buffer.append((char) c);
        }
        txt = new String(buffer);
        Pattern pattern = Pattern.compile("[Unsupported]");
        Matcher matcher = pattern.matcher(txt);
        txt = matcher.replaceAll("#RXY");
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        bufferedWriter.write(txt);

        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();

    }
}