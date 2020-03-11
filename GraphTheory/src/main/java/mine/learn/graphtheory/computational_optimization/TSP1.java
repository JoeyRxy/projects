package mine.learn.graphtheory.computational_optimization;

import java.util.LinkedList;
import java.util.List;

/**
 * TSP
 */
@Deprecated
public class TSP1 {

    private double[][] g;
    private int V;
    // private int[] path;
    private List<Integer> path;
    private MyList S;
    private int s;

    public TSP1(double[][] g) {
        this.g = g;
        V = g.length;
        // path = new int[V];
        path = new LinkedList<>();
        S = new MyList();
        // add all vertex to S
        s = 0;
        for (int i = 0; i < V; i++) {
            S.add(i);
        }

    }

    public double res() {
        return dfs(s);
    }

    private double inf = Double.POSITIVE_INFINITY;

    private double dfs(int v) {
        if (S.isEmpty()) {
            return g[v][s];
        }
        double min = inf;
        Node cur = S.head.next;
        // 遍历S
        while (cur != S.tail) {
            if (g[v][cur.data] == Double.POSITIVE_INFINITY) {
                cur = cur.next;
                continue;
            }
            S.remove(cur);
            double ret = g[v][cur.data] + dfs(cur.data);
            if (ret < min) {
                min = ret;
            }
            S.reset(cur);
            cur = cur.next;
        }
        return min;
    }

    // public Iterable<Integer> path() {
    // LinkedList<Integer> list = new LinkedList<>();
    // list.push(s);
    // int v = path[s];
    // while (v != s) {
    // list.push(v);
    // v = path[v];
    // }
    // return list;
    // }

    // public String debugHelper(Node cur) {
    // StringBuilder builder = new StringBuilder();
    // int v = cur.data;
    // while (v != s) {
    // builder.append(v + ">-");
    // v = edgeTo[v];
    // }
    // builder.append(s);
    // return builder.reverse().toString();
    // }

    public static void main(String[] args) {
        // double[][] g = { { 0, 3, 1, 5, 8 }, { 3, 0, 6, 7, 9 }, { 1, 6, 0, 4, 2 }, {
        // 5, 7, 4, 0, 3 },
        // { 8, 9, 2, 3, 0 } };
        double inf = Double.POSITIVE_INFINITY;
        // double[][] g = { { 0, 3, inf, 8, 9 }, { 3, 0, 3, 10, 5 }, { inf, 3, 0, 4, 3
        // }, { 8, 10, 4, 0, 20 },
        // { 9, 5, 3, 20, 0 } };
        double[][] g = { { 0, 3, 10, 5 }, { 3, 0, 4, 3 }, { 10, 4, 0, 20 }, { 5, 3, 20, 0 } };
        TSP1 tsp = new TSP1(g);
        double res = tsp.res();
        System.out.println(res);
        // System.out.println(tsp.path);
    }
}