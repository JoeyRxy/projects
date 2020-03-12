package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.List;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;

/**
 * FloydWarshall
 */
public class FloydWarshall {

    private double[][] dp;
    private WeightedDirectedEdge[][] path;// 从i到j经过{0,1,...,n-1}个节点，到j前的一个边
    private int V;

    public FloydWarshall(EdgeWeightedDiGraph g) {
        this.V = g.V();
        dp = new double[V][V];
        path = new WeightedDirectedEdge[V][V];

        // init
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                dp[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < V; i++) {
            for (WeightedDirectedEdge e : g.adjOf(i)) {
                dp[e.from()][e.to()] = e.weight();
                path[e.from()][e.to()] = e;
            }

            dp[i][i] = 0;
        }
        // cal
        double ret;
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                if (path[i][k] != null)
                    for (int j = 0; j < V; j++) {
                        ret = dp[i][k] + dp[k][j];
                        if (dp[i][j] > ret) {
                            dp[i][j] = ret;
                            path[i][j] = path[i][k];
                        }
                    }
            }
        }
    }

    public double dist(int from, int to) {
        return dp[from][to];
    }

    public boolean hasPath(int from, int to) {
        return !Double.isInfinite(dp[from][to]);
    }

    public LinkedList<WeightedDirectedEdge> path(int from, int to) {
        if (Double.isInfinite(dp[from][to]))
            return null;
        LinkedList<WeightedDirectedEdge> list = new LinkedList<>();
        WeightedDirectedEdge e = path[from][to];
        while (e != null) {
            list.add(e);
            e = path[e.to()][to];
        }
        // for (WeightedDirectedEdge e = path[from][to]; e != null; e =
        // path[e.from()][to]) {
        // list.add(e);
        // }
        return list;
    }

    public static void main(String[] args) {
        double[][] g = { { 0, 3, Double.POSITIVE_INFINITY, 8, 9 }, { 3, 0, 3, 10, 5 },
                { Double.POSITIVE_INFINITY, 3, 0, 4, 3 }, { 8, 10, 4, 0, 20 }, { 9, 5, 3, 20, 0 } };
        FloydWarshall spt = new FloydWarshall(new EdgeWeightedDiGraph(g));
        double dist = spt.dist(0, 2);
        LinkedList<WeightedDirectedEdge> path = spt.path(0, 2);
        System.out.println(dist + " : " + path);
    }
}