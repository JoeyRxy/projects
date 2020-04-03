package mine.learn.graphtheory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.junit.Test;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.util.Helpers;

/**
 * AppTest2
 */
public class AppTest2 {

    @Test
    public void testDirectedCycles() throws NumberFormatException, IOException {
        EdgeWeightedDiGraph g = (EdgeWeightedDiGraph) Helpers.getGraph("EWD.txt", EdgeWeightedDiGraph.class);
        DirectedCycles cycles = new DirectedCycles(g);
        System.out.println(cycles);
    }

    @Test
    public void ramdomDeleteRaws() {

        InputStreamReader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("largeEWG.txt"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/largeEWG.txt")));
            StringBuilder builder = new StringBuilder();
            writer.write("1000000\n");
            writer.write("xxx\n");
            int c;
            int lineCount = 0, afterCount = 0;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
                if (c == '\n') {
                    lineCount++;
                    if (Math.random() < 0.5) {
                        afterCount++;
                        writer.write(builder.toString());
                    }
                    builder = new StringBuilder();
                }
            }
            reader.close();
            writer.close();
            System.out.println(afterCount);
            System.out.println(lineCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPrint() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("C:/Users/Rxy/Desktop/largeEWG.txt")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/largeEWG.txt")));
        String line;
        writer.write(reader.readLine() + "\n");
        writer.write(reader.readLine() + "\n");
        while ((line = reader.readLine()) != null)
            if (Math.random() < 0.1)
                writer.write(line + "\n");

        reader.close();
        writer.close();
    }

    @Test
    public void testRandom() {
        Random r = new Random(System.currentTimeMillis());
        boolean[] x = new boolean[10000000];
        for (int i = 0; i < x.length; i++) {
            x[i] = r.nextGaussian() < .5;
        }
        int count = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i])
                count++;
        }
        System.out.println((double) count / x.length);
    }

    @Test
    public void testBigGraph() throws IOException {
        long start, end;
        start = System.currentTimeMillis();
        EdgeWeightedDiGraph g = Helpers.parseJSON(new File("C:\\Users\\Rxy\\Downloads\\Compressed\\pcb3038.json"),
                null);
        end = System.currentTimeMillis();
        System.out.println("time : " + (end - start) + " ms.");
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("pcb3038.txt")));
        writer.write("time : " + (end - start) + " ms.");
        writer.write(g.toString());
        writer.close();
    }

    @Test
    public void testExp() {
        Random r = new Random(System.currentTimeMillis());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Math.exp(r.nextGaussian() * 1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms.");
    }

    public void f(int[] x) {
        x[0] = 1000;
    }

    @Test
    public void testtest() {
        int[] a = { -1 };
        f(a);
        System.out.println(a[0]);
    }
}