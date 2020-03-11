package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.List;

/**
 * FloydWarshall
 */
public class FloydWarshall {

    private double[][] dp;
    private int[][] path;// next[i][j]=从i到j经过{0,1,...,n-1}个节点

    public FloydWarshall(final double[][] g) {
        dp = new double[g.length][g.length];
        path = new int[g.length][g.length];
        // init
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                dp[i][j] = g[i][j];
                path[i][j] = j;
            }
        }
        // cal
        double ret;
        for (int k = 0; k < g.length; k++) {
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
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