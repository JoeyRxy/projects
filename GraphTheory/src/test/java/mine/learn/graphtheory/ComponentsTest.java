package mine.learn.graphtheory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.util.Helpers;

/**
 * ComponentsTest
 */
public class ComponentsTest {

    @Test
    public void testComponents() throws IOException {
        EdgeWeightedGraph g = (EdgeWeightedGraph) Helpers.getGraph("largeEWG.txt", EdgeWeightedGraph.class);
        long start = System.currentTimeMillis();
        Components components = new Components(g);
        long end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms");
        int count = components.count();
        System.out.println("components' count : " + count);
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("fuck.txt")));
        for (int i = 0; i < count; i++) {
            writer.write(i + " : " + components.sizeOf(i + 1) + "\n");
        }
        writer.close();
    }
}