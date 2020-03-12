package mine.learn.graphtheory;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.computational_optimization.TSP1;
import mine.learn.graphtheory.computational_optimization.TSP2;
import mine.learn.graphtheory.util.Helpers;
import mine.learn.graphtheory.util.IndexedPriorityQueue;
import mine.learn.graphtheory.util.PriorityQueueM;

public class AppTest {

    /**
     * Pair
     */
    public class Pair implements Comparable<Pair> {

        Integer key;
        String val;

        @Override
        public int compareTo(Pair o) {
            return val.compareTo(o.val);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + Objects.hash(key);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            return Objects.equals(key, other.key);
        }

        private AppTest getEnclosingInstance() {
            return AppTest.this;
        }

        public Pair(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return String.format("Pair [%-5d : %s]", key, val);
        }

    }

    /**
     * test res :
     * <p>
     * duration : 5034 ms.
     * <p>
     * duration : 7581 ms.
     * <p>
     * pq size : 864834
     */
    @Test
    public void testPriorityQueueM() {
        int n = 1000000;
        PriorityQueueM<Pair> pq = new PriorityQueueM<>(n);
        Random r = new Random(System.currentTimeMillis());
        int len = 2 * n;
        long start = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            pq.add(new Pair(r.nextInt(n), randomString(r, r.nextInt(80) + 1)));
        }
        long end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms.");
        String[] t = new String[pq.size()];
        int idx = 0;
        start = System.currentTimeMillis();
        while (!pq.isEmpty()) {
            String tmp = pq.poll().val;
            t[idx] = tmp;
            idx++;
        }
        end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms.");
        System.out.println("pq size : " + t.length);
        // for (int i = 1; i < t.length; i++) {
        // assertTrue("问题出在第 " + i + " 行", t[i].compareTo(t[i - 1]) >= 0);
        // }
    }

    /**
     * test res :
     * <p>
     * duration : 3265 ms.
     * <p>
     * duration : 1835 ms.
     * <p>
     * pq size : 864395
     */
    @Test
    public void testIndexedPriorityQueue() {
        int n = 1000000;
        IndexedPriorityQueue<String> pq = new IndexedPriorityQueue<>(n);
        String[] keys = new String[n];
        Random r = new Random(System.currentTimeMillis());
        int len = 2 * n;
        long start = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            int idx = r.nextInt(n);
            String val = randomString(r, r.nextInt(80) + 1);
            pq.add(idx, val);
            keys[idx] = val;
        }
        long end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms.");
        String[] t = new String[pq.size()];
        int idx = 0;
        start = System.currentTimeMillis();
        while (!pq.isEmpty()) {
            int tmp = pq.poll();
            t[idx] = keys[tmp];
            idx++;
        }
        end = System.currentTimeMillis();
        System.out.println("duration : " + (end - start) + " ms.");
        System.out.println("pq size : " + t.length);
        // for (int i = 1; i < t.length; i++) {
        // assertTrue("问题出在第 " + i + " 行", t[i].compareTo(t[i - 1]) >= 0);
        // }
    }

    private String randomString(Random r, int len) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            builder.append((char) (r.nextInt(26) + 65));
        }
        return builder.toString();
    }

    @Test
    public void testHashCode() {
        int v = 2, w = 4;
        WeightedEdge e1 = new WeightedEdge(v, w, 1.0);
        WeightedEdge e2 = new WeightedEdge(w, v, 1.0);
        System.out.println(e1.hashCode() + " : " + e2.hashCode());
        System.out.println(e1.equals(e2));
        System.out.println(e1.compareTo(e2));
    }

    @Test
    public void testString() {
        String s1 = "hello";
        String s2 = "hello";
        System.out.println(s1 + s2);
    }

    @Test
    public void testDijkstra() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("EWD.txt")));
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(Integer.parseInt(reader.readLine()));
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            String[] split = (" " + line).split(" +");
            g.addEdge(new WeightedDirectedEdge(Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                    Double.parseDouble(split[3])));
            line = reader.readLine();
        }

        Dijkstra dijkstra = new Dijkstra(g, 0);
        System.out.println(dijkstra.stringPathTo(2));
    }

    @Test
    public void testEWDn() throws NumberFormatException, IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("tinyEWDn.txt")));
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(Integer.parseInt(reader.readLine()));
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(" +");
            g.addEdge(new WeightedDirectedEdge(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                    Double.parseDouble(split[2])));
            line = reader.readLine();
        }

        EdgeWeightedDiGraph g2 = new EdgeWeightedDiGraph(g.V());
        double min = Double.POSITIVE_INFINITY;
        for (WeightedDirectedEdge e : g.edges()) {
            if (min > e.weight())
                min = e.weight();
        }
        for (WeightedDirectedEdge e : g.edges()) {
            g2.addEdge(new WeightedDirectedEdge(e.from(), e.to(), e.weight() - min));
        }

        Dijkstra dijkstra1 = new Dijkstra(g, 0);
        for (int i = 1; i < g.V(); i++) {
            System.out.println("0 -> " + i + " || " + dijkstra1.stringPathTo(i));
        }
        System.out.println("====================");
        Dijkstra dijkstra2 = new Dijkstra(g2, 0);
        for (int i = 1; i < g2.V(); i++) {
            System.out.println("0 -> " + i + " || " + dijkstra2.stringPathTo(i));
        }
    }

    @Test
    public void testEquals() {
        Double d1 = 3.2;
        Double d2 = 3.2;
        System.out.println(d1.equals(d2));
        System.out.println(d1 == d2);
    }

    @Test
    public void testGraph() throws IOException {
        System.out.println((EdgeWeightedGraph) Helpers.getGraph("mediumEWG.txt", EdgeWeightedGraph.class));
    }

    @Test
    public void testSort() {
        class Data {
            int x;
            int y;

            public Data(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public String toString() {
                return "Data [x=" + x + ", y=" + y + "]";
            }

        }
        class DataCmp implements Comparator<Data> {

            @Override
            public int compare(Data o1, Data o2) {
                if (o1.x > o2.x)
                    return 1;
                else if (o1.x < o2.x)
                    return -1;
                else {
                    if (o1.y > o2.y)
                        return -1;
                    else if (o1.y < o2.y)
                        return 1;
                    else
                        return 0;
                }
            }

        }
        Random r = new Random(System.currentTimeMillis() % 997);
        Data[] datas = new Data[100];
        for (int i = 0; i < datas.length; i++) {
            datas[i] = new Data(r.nextInt(10), r.nextInt(50));
        }
        for (Data data : datas) {
            System.out.println(data);
        }
        Arrays.sort(datas, new DataCmp());
        System.out.println("========After Sort==========");
        for (Data data : datas) {
            System.out.println(data);
        }
    }

    @Test
    public void testLinkedList() {
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        for (Integer i : stack) {
            System.out.println(i);
        }
    }

    @Test
    public void testHashArray() {
        Boolean[] b = new Boolean[10];
        for (int i = 0; i < b.length; i++)
            b[i] = false;

        Set<Boolean[]> tSet = new HashSet<>();
        tSet.add(b);
        b[5] = true;
        tSet.add(b);
        b[3] = true;
        tSet.add(b);
    }

    @Test
    public void TestTSP() {
        int n = 200;
        double[][] g = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = Math.random() * 10;
            }
        }
        for (int i = 0; i < n; i++) {
            g[i][i] = 0;
        }
        // for (int i = 0; i < g.length; i++) {
        // for (int j = 0; j < g.length; j++) {
        // System.out.print(String.format("%4.2f ", g[i][j]));
        // }
        // System.out.println();
        // }
        TSP2 tsp = new TSP2(new EdgeWeightedDiGraph(g), new int[] { 3, 5, 7, 9, 11, 20, 19 });
        long start = System.currentTimeMillis();
        double res = tsp.cal();
        long end = System.currentTimeMillis();
        System.out.println(res);
        System.out.println(end - start + " ms");
        var path = tsp.path();
        System.out.println(path);
        // check
        double s = 0;
        for (WeightedDirectedEdge e : path) {
            s += e.weight();
        }
        System.out.println(s);
        assertTrue((Math.abs(res - s) < 1e-8));
    }

    @Test
    public void testRotateLeft() {
        int x = ~1;
        System.out.println(x);
        System.out.println((x >>> -1));
        System.out.println((x << 1));
    }

    @Test
    public void testInfinity() {
        double d = Double.POSITIVE_INFINITY;
        System.out.println(d);
        System.out.println(d + 1);
    }

    @Test
    public void dasfadfadfadf() {
        int n = 30;
        System.out.println((long) n * (1 << n) * Double.SIZE);
    }

    @Test
    public void testFloydWarshall() {
        int n = 200;
        double[][] g = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = Math.random() * 10;
            }
        }
        for (int i = 0; i < n; i++) {
            g[i][i] = 0;
        }
        // double[][] g = { { 0, 3, Double.POSITIVE_INFINITY, 8, 9 }, { 3, 0, 3, 10, 5
        // },
        // { Double.POSITIVE_INFINITY, 3, 0, 4, 3 }, { 8, 10, 4, 0, 20 }, { 9, 5, 3, 20,
        // 0 } };
        long start = System.currentTimeMillis();
        EdgeWeightedDiGraph g2 = new EdgeWeightedDiGraph(g);
        long end1 = System.currentTimeMillis();
        FloydWarshall spt = new FloydWarshall(g2);
        long end2 = System.currentTimeMillis();
        System.out.println(end1 - start);
        System.out.println(end2 - end1);

        // double dist = spt.dist(0, 2);
        // LinkedList<WeightedDirectedEdge> path = spt.path(0, 2);
        // System.out.println(dist + " : " + path);
        // for (int i = 0; i < g.length; i++) {
        // for (int j = 0; j < g.length; j++) {
        // System.out.println(
        // i + " -> " + j + " : " + spt.dist(i, j) + " " + g[i][j] + ", path : " +
        // spt.path(i, j));
        // }
        // }
    }
}
