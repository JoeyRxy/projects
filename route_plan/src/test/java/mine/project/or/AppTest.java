package mine.project.or;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

import mine.project.bean.EdgeWeightedDiGraph;
import mine.project.util.DataProcessor;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testDirectedEdge() {
        // DirectedEdge e = new DirectedEdge(10, 24, 20.7);

        // System.out.println(e);
    }

    @Test
    public void testMap() {
        Map<Integer, String> map1 = new HashMap<>();
        Map<Integer, String> map2 = new TreeMap<>();

        map1.put(2, "b");
        map2.put(2, "b");
        map1.put(7, "g");
        map2.put(7, "g");
        map1.put(3, "c");
        map2.put(3, "c");
        map1.put(1, "a");
        map2.put(1, "a");
        map1.put(5, "e");
        map2.put(5, "e");
        map1.put(4, "d");
        map2.put(4, "d");
        map1.put(6, "f");
        map2.put(6, "f");

        System.out.println("============hash map=================");
        Iterator<Entry<Integer, String>> iter1 = map1.entrySet().iterator();
        while (iter1.hasNext())
            System.out.println(iter1.next());

        System.out.println("============tree map=================");
        Iterator<Entry<Integer, String>> iter2 = map2.entrySet().iterator();
        while (iter2.hasNext())
            System.out.println(iter2.next());

        for (Integer key : map1.keySet()) {
            System.out.println(key + " === " + map1.get(key));
        }

        for (Integer key : map2.keySet()) {
            System.out.println(key + " === " + map1.get(key));
        }
    }

    @Test
    public void testEdgeWeightedGraph() throws IOException {
        EdgeWeightedDiGraph g = DataProcessor.process("mediumEWD.txt");
        String path = getClass().getClassLoader().getResource("mediumEWD.txt").getPath();
        File file = new File(new File(path).getParent() + "/EdgeWeightedDiGraph.txt");
        Writer writer = new FileWriter(file);
        writer.write(g.toString());
        writer.close();
    }

    @Test
    public void testReverseEWDG() throws IOException, CloneNotSupportedException {
        EdgeWeightedDiGraph g = DataProcessor.process("tinyEWDAG.txt");
        String path = getClass().getClassLoader().getResource("mediumEWD.txt").getPath();
        File file = new File(new File(path).getParent() + "/ReverseEdgeWeightedDiGraph.txt");
        Writer writer = new FileWriter(file);
        writer.write(g.reverse().toString());
        writer.close();
    }

    @Test
    public void testDijkstra() throws IOException {
        EdgeWeightedDiGraph g = DataProcessor.process("mediumEWD.txt");
        URL resource = getClass().getClassLoader().getResource("");
        String path = resource.getPath();
        File file = new File(path + "/DijkstraShortestPath.txt");
        Writer writer = new FileWriter(file);
        Dijkstra dijkstra = new Dijkstra(g, 5 + "");
        for (String vertex : g.vertexSet()) {
            String shortestPath = dijkstra.PathPrinter(vertex);
            writer.write(shortestPath + "\n");
        }
        writer.close();
    }

    @Test
    public void testDirectedCycle() throws IOException {
        EdgeWeightedDiGraph g = DataProcessor.process("mediumEWD.txt");
        URL resource = getClass().getClassLoader().getResource("");
        String path = resource.getPath();
        File file = new File(path + "/DirectedCycle.txt");
        DirectedCycle cycle = new DirectedCycle(g);
        Writer writer = new FileWriter(file);
        writer.write(cycle.toString());
        writer.close();
    }

}
