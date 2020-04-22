package mine.learn.graphtheory.computational_optimization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class TSPDP2 {

    private HashMap<HashSet<Integer>, Double>[] memos;
    private HashMap<HashSet<Integer>, Integer>[] pathFrom;
    private int V;
    private double minDist;
    private double[][] g;
    private int[] minPath;
    private double[][] memo1;
    private int[][] pathFrom1;

    public TSPDP2(double[][] g) {
        this.g = g;
        V = g.length;
        memos = new HashMap[V];
        pathFrom = new HashMap[V];
        for (int i = 0; i < V; i++) {
            memos[i] = new HashMap<>();
            memos[i].put(null, g[i][0]);
            pathFrom[i] = new HashMap<>();
            pathFrom[i].put(null, 0);
        }
        HashSet<Integer> S0 = new HashSet<>(V - 1);
        for (int i = 1; i < V; i++) {
            S0.add(i);
        }
        minDist = memoDFS2(0, S0);
        minPath = new int[V];
        for (int i = 1; i < V; i++) {
            minPath[i] = pathFrom[minPath[i - 1]].get(S0);
            S0.remove(minPath[i]);
        }
    }

    private double memoDFS2(int u, HashSet<Integer> S) {
        Double _t = memos[u].get(S.size() == 0 ? null : S);
        if (_t != null)
            return _t.doubleValue();
        double ret;
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (Integer v : S) {
            if (Double.isInfinite(g[u][v]))
                continue;
            HashSet<Integer> tmp = (HashSet<Integer>) S.clone();
            tmp.remove(v);
            ret = g[u][v] + memoDFS2(v, tmp);
            if (min > ret) {
                min = ret;
                w = v;
            }
        }
        memos[u].put(S, min);
        pathFrom[u].put(S, w);
        return min;
    }

    /**
     * @return the minDist
     */
    public double getMinDist() {
        return minDist;
    }

    /**
     * @return the minPath
     */
    public int[] getMinPath() {
        return minPath;
    }

    public void contrast() {
        memo1 = new double[V][1 << V];
        pathFrom1 = new int[V][1 << V];
        int S0 = (1 << V) - 2;
        // init
        for (int v = 0; v < V; v++) {
            memo1[v][0/** 未经过节点集合为空 */
            ] = g[v][0];
            pathFrom1[v][0/** 未经过节点集合为空 */
            ] = 0;// 利用-1表示source，因为source本身可能和set的下标重合
        }
        double min = memoDFS(0, S0);
        System.out.println(min);
        int[] path = new int[V];
        path[0] = 0;
        for (int i = 1; i < V; i++) {
            path[i] = pathFrom1[path[i - 1]][S0];
            S0 &= (~(1 << path[i]));
        }
        System.out.println(Arrays.toString(path));
    }

    private double memoDFS(int u, int S) {
        if (memo1[u][S] > 0) {
            return memo1[u][S];
        }
        double ret;
        int t = ~1, x = 1;
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (int i = 0; i < V; i++, x <<= 1, t = (t << 1) | (t >>> -1)) {
            if (Double.isInfinite(g[u][i]))
                continue;
            if ((S & x) > 0) {// 遍历S中的节点
                ret = g[u][i] + memoDFS(i, S & t);
                if (min > ret) {
                    min = ret;
                    w = i;
                }
            }
        }
        memo1[u][S] = min;
        pathFrom1[u][S] = w;
        return min;
    }

    public static void main(String[] args) {
        int n = 16;
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
        TSPDP2 tspdp2 = new TSPDP2(g);
        double minDist2 = tspdp2.getMinDist();
        int[] minPath2 = tspdp2.getMinPath();
        System.out.println(minDist2);
        System.out.println(Arrays.toString(minPath2));

        System.out.println("============ 对比 ================");
        tspdp2.contrast();
    }
}