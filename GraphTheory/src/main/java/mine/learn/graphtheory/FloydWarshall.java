package mine.learn.graphtheory;

import java.util.LinkedList;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * FloydWarshall
 */
public class FloydWarshall {

    private double[][] dp;
    private int[][] path;// next[i][j]=从i到j经过{0,1,...,n-1}个节点，到j之前的一个节点
    private int V;

    public FloydWarshall(EdgeWeightedDiGraph g) {
        this.V = g.V();
        dp = new double[V][V];
        path = new int[V][V];
        // init
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dp[i][j] = g.wij(i, j);
                if (j == i)
                    path[i][i] = -1;
                else
                    path[i][j] = j;
            }
        }
        // cal
        double ret;
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                if (dp[i][k] == -1)
                    continue;
                for (int j = 0; j < V; j++) {
                    ret = dp[i][k] + dp[k][j];
                    if (dp[i][j] > ret) {// 从i到j经过k点
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

    public LinkedList<Integer> path(int from, int to) {
        if (Double.isInfinite(dp[from][to]))
            return null;
        LinkedList<Integer> list = new LinkedList<>();
        list.add(from);
        while (from != to) {
            from = path[from][to];
            list.add(from);
        }
        return list;
    }

    /**
     * 
     * @param from include
     * @param to   exclude
     * @return
     */
    public LinkedList<Integer> pathExclude(int from, int to) {
        if (Double.isInfinite(dp[from][to]))
            return null;
        LinkedList<Integer> list = new LinkedList<>();
        while (from != to) {
            list.add(from);
            from = path[from][to];
        }
        return list;
    }
}