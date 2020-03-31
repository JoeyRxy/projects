package mine.learn.graphtheory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import mine.learn.graphtheory.computational_optimization.heuristic.TSP3;
import mine.learn.graphtheory.computational_optimization.heuristic.TSP4;
import mine.learn.graphtheory.computational_optimization.heuristic.TSP5;
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
    public void TestTSP3() {
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
        TSP3 tsp = new TSP3(new EdgeWeightedDiGraph(g));
        long start = System.currentTimeMillis();
        double res = tsp.cal();
        long end = System.currentTimeMillis();
        System.out.println(res);
        System.out.println(end - start + " ms");
        var path = tsp.path();
        System.out.println(path.size());
        // check
        // double s = 0;
        // for (WeightedDirectedEdge e : path) {
        // s += e.weight();
        // }
        // System.out.println(s);
        // assertTrue((Math.abs(res - s) < 1e-8));
    }

    public static double sumPath(List<WeightedDirectedEdge> path) {
        double s = 0;
        for (WeightedDirectedEdge e : path) {
            s += e.weight();
        }
        return s;
    }

    @Test
    public void testTSP() {
        int n = 1100;
        Random r = new Random(System.currentTimeMillis());
        double[][] g = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = Math.abs(r.nextGaussian() * r.nextDouble()) * 1000;
            }
        }
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g.length; j++)
                if (Math.random() < 0.10)
                    g[i][j] = Double.POSITIVE_INFINITY;

        for (int i = 0; i < n; i++) {
            g[i][i] = 0;
        }
        EdgeWeightedDiGraph gg = new EdgeWeightedDiGraph(g);
        System.out.println("图中有" + gg.V() + "个节点");
        System.out.println("图中有" + gg.edges().size() + "条边.");
        int[] SETS = { 5, 9, 13, 36, 86, 47, 55, 66, 77, 88, 99, 111, 123, 146, 132, 167, 189, 112, 98 };
        System.out.println("需经过点集集合的长度：" + SETS.length);
        TSP2 tsp = new TSP2(gg, SETS);
        long start = System.currentTimeMillis();
        double res = tsp.cal();
        long end = System.currentTimeMillis();
        System.out.println("动态算法总边长：" + res);
        System.out.println("耗时：" + (end - start) + " ms");
        var path = tsp.path();
        // System.out.println("路径序列：" + path);
        System.out.println("路径：\n" + path);
        System.out.println("=====================");

        // start = System.currentTimeMillis();
        // TSP5 tsp5 = new TSP5(gg, SETS, 120, 1, 0.997, 1000, 0.5);
        // end = System.currentTimeMillis();
        // System.out.println("模拟退火总边长：" + tsp5.getBestDist());
        // List<WeightedDirectedEdge> path2 = tsp5.getPath();
        // // System.out.println("路径序列：\n" + path2);
        // // System.out.println("长度：" + sumPath(path2));
        // System.out.println("耗时：" + (end - start) + " ms.");
        // System.out.println("路径：\n" + path2);
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
    public void testFloydWarshall() throws IOException {
        EdgeWeightedDiGraph graph = Helpers.parseJSON("pcb1173.json");
        long end1 = System.currentTimeMillis();
        FloydWarshall floydwarshall = new FloydWarshall(graph);
        long end2 = System.currentTimeMillis();

        // double dist = floydwarshall.dist(0, 2);
        // LinkedList<WeightedDirectedEdge> path = floydwarshall.path(0, 2);
        // System.out.println(dist + " : " + path);
        // for (int i = 0; i < graph.V(); i++) {
        // for (int j = 0; j < graph.V(); j++) {
        // System.out.println(i + " -> " + j + " : " + floydwarshall.dist(i, j) + ",
        // path : " +
        // floydwarshall.path(i, j));
        // }
        // }
        System.out.println((end2 - end1) + " ms");
    }

    @Test
    public void testTSP5() throws NumberFormatException, IOException {
        long start = 0, end = 0;
        start = System.currentTimeMillis();
        // EdgeWeightedDiGraph graph = (EdgeWeightedDiGraph)
        // Helpers.getGraph("largeEWG.txt", EdgeWeightedDiGraph.class);
        // EdgeWeightedDiGraph graph = Helpers.parseJSON("d1291.json");
        EdgeWeightedDiGraph graph = Helpers.parseJSON("src/main/resources/pcb1173.json");
        // EdgeWeightedDiGraph graph = Helpers.parseJSON("kroB200.json");
        // EdgeWeightedDiGraph graph = Helpers.parseJSON("ch130.json");

        end = System.currentTimeMillis();
        System.out.println("读图时间：" + (end - start) + " ms.");
        int[] set = new int[graph.V()];
        for (int i = 0; i < set.length; i++) {
            set[i] = i;
        }
        double a = 0.999;
        int markov = 5000;
        double p = 0.5;
        System.out.println("t0 : " + 100 + ", tf : " + 1 + ", a : " + a + ", markov : " + markov + ", p : " + p);
        double min = Double.POSITIVE_INFINITY;
        double max = 0;
        double s = 0;
        int times = 1;
        for (int k = 0; k < times; k++) {
            // System.out.println("========================");
            start = System.currentTimeMillis();
            TSP5 tsp5 = new TSP5(graph, set, 100, 1, a, markov, p);
            end = System.currentTimeMillis();
            double bestDist = tsp5.getBestDist();
            s += bestDist;
            if (max < bestDist)
                max = bestDist;
            if (min > bestDist)
                min = bestDist;
            System.out.println("my score : " + bestDist);
            List<WeightedDirectedEdge> path = tsp5.getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("tsp5 pcb1173 path 3")));
            writer.write(path.toString());
            writer.close();
            System.out.println(sumPath(path));
        }
        System.out.println("duration : " + (end - start) + " ms.");
        System.out.println("my min score : " + min);
        System.out.println("my max score : " + max);
        System.out.println("my avrage : " + (s / times));
        // System.out.println("==========================");
        // TSP3 tsp3 = new TSP3(graph);
        // start = System.currentTimeMillis();
        // double cal = tsp3.cal();
        // end = System.currentTimeMillis();
        // System.out.println(cal);
        // System.out.println(end - start);
    }

    @Test
    public void testTSP53() throws IOException {
        EdgeWeightedDiGraph graph = Helpers.parseJSON("pcb1173.json");
        FloydWarshall floydwarshall = new FloydWarshall(graph);
        double[][] g = new double[graph.V()][graph.V()];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                g[i][j] = floydwarshall.dist(i, j);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("floydwarshall")));
        writer.write(Arrays.deepToString(g));
        writer.close();
    }

    @Test
    public void testTSP1() {
        int n = 10;
        Random r = new Random(System.currentTimeMillis());
        double[][] g = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = Math.abs(r.nextGaussian() * r.nextDouble()) * 200;
            }
        }
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g.length; j++)
                if (Math.random() < 0.10)
                    g[i][j] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            g[i][i] = 0;
        }
        TSP1 tsp1 = new TSP1(g);
        long start, end;
        start = System.currentTimeMillis();
        double res = tsp1.res();
        end = System.currentTimeMillis();
        System.out.println(res);
        System.out.println("duration : " + (end - start) + " ms");
    }

    @Test
    public void testTSPContrast() throws IOException {
        // int n = 1100;
        // Random r = new Random(System.currentTimeMillis());
        // double[][] graph = new double[n][n];
        // for (int i = 0; i < n; i++) {
        // if(r.nextDouble()<0.1) r.setSeed(System.currentTimeMillis());
        // for (int j = 0; j < n; j++) {
        // graph[i][j] = Math.abs(r.nextGaussian() * r.nextDouble()) * 1000;
        // }
        // }
        // for (int i = 0; i < graph.length; i++)
        // for (int j = 0; j < graph.length; j++)
        // if (Math.random() * r.nextDouble() < 0.50)
        // graph[i][j] = Double.POSITIVE_INFINITY;

        // for (int i = 0; i < n; i++) {
        // graph[i][i] = 0;
        // }
        // EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(graph);
        long start, end;
        EdgeWeightedDiGraph g = Helpers.parseJSON("pcb1173.json");
        // int[] set = { 0, 2, 3, 5, 10, 12, 13, 19, 29, 33, 36, 38, 39, 23, 34, 67, 78
        // };
        int[] set = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            set[i] = i;
        }
        System.out.println("============ Graph Info ====================");
        System.out.println("图有" + g.V() + "个节点");
        System.out.println("图有" + g.E() + "条边");
        System.out.println("需遍历的节点共" + set.length + "个");
        // System.out.println("============================================");
        // TSP2 tsp2 = new TSP2(g, set);
        // start = System.currentTimeMillis();
        // double dist2 = tsp2.cal();
        // end = System.currentTimeMillis();
        // System.out.println("============ TSP Exact Algo ================");
        // System.out.println("距离：" + dist2);
        // System.out.println("耗时：" + (end - start) + " ms.");
        System.out.println("============ TSP Heuristic Algo ============");
        start = System.currentTimeMillis();
        TSP5 tsp5 = new TSP5(g, set, 120, 1, 0.99999, 30000, 0.5);
        end = System.currentTimeMillis();
        System.out.println("距离：" + tsp5.getBestDist());
        System.out.println("耗时：" + (end - start) + " ms.");
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("path pdb1173")));
        writer.write("距离：" + tsp5.getBestDist() + "\n");
        writer.write("耗时：" + (end - start) + " ms." + "\n");
        writer.write(tsp5.getPath().toString());
        writer.close();
    }

}
