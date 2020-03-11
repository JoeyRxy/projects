package mine.learn.graphtheory.computational_optimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mine.learn.graphtheory.FloydWarshall;

/**
 * TSP2
 * <p>
 * dp[v][S] :=
 * <p>
 * if |S| != 1 :
 * <p>
 * &emsp;min{ dp[w][S-{v}] + g[v][w] | w in S}
 * <p>
 * else :
 * <p>
 * &emsp;g[v][S];
 */
public class TSP2 {
    // class MySet {
    // long S;
    // /** size <= 64 */
    // int size;

    // }

    private double[][] memo;
    private int[][] pathFrom;
    private final double[][] g;
    private final int source;
    private final int[] set;
    /** 不包括源节点source */
    private final int S0;

    /**
     * 可能后期还要传入一个set表示需要经过的所有点的集合，因为未必所有点都需要经过
     * <p>
     * 无set参数时，g.length即为set.size()
     * 
     * @param set 需要经过的所有节点，要求个数不超过30
     */
    public TSP2(double[][] graph, int[] set) {
        if (set.length > 30)
            throw new IllegalArgumentException("问题规模过大，不可计算");
        int len = set.length;
        this.g = new double[len][len];// 加上source
        spt = new FloydWarshall(graph);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                g[i][j] = spt.dist(set[i], set[j]);
            }
        }
        memo = new double[len][1 << len];
        pathFrom = new int[len][1 << len];
        this.set = set;
        S0 = (1 << len) - 2;
        source = 0;
        // init
        for (int v = 0; v < len; v++) {
            memo[v][0/** 未经过节点集合为空 */
            ] = g[v][source];
            pathFrom[v][0/** 未经过节点集合为空 */
            ] = source;// 利用-1表示source，因为source本身可能和set的下标重合
        }

        // 本身计算所有的|S|=card的过程就是一个dfs的组合计算过程
        // for (int card = 2; card <= g.length; card++) {// card{S}从2到g.length
        // for (int v = 0; v < g.length; v++) {

        // }
        // }
    }

    /**
     * 当前节点为set[u]，当前未经过的节点集合为S
     * 
     * @param u
     * @param S
     * @return
     */
    private double memoDFS(int u, int S) {
        if (memo[u][S] > 0) {
            return memo[u][S];
        }
        double ret;
        int t = ~1, x = 1;
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (int i = 0; i < set.length; i++, x <<= 1, t = (t << 1) | (t >>> -1)) {
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
        memo[u][S] = min;
        pathFrom[u][S] = w;
        return min;
    }

    private FloydWarshall spt;

    public double cal() {
        return memoDFS(0, S0);
    }

    public List<Integer> path() {
        LinkedList<Integer> list = new LinkedList<>();
        int v = pathFrom[source][S0], w = source;
        list.addAll(spt.pathExclude(set[w], set[v]));
        int S = S0;
        while (v != source) {
            S &= (~(1 << v));
            w = v;
            v = pathFrom[v][S];
            list.addAll(spt.pathExclude(set[w], set[v]));
        }
        return list;
    }

    public static void main(String[] args) {
        // double[][] g = { { 0, 3, 1, 5, 8 }, { 3, 0, 6, 7, 9 }, { 1, 6, 0, 4, 2 }, {
        // 5, 7, 4, 0, 3 },
        // { 8, 9, 2, 3, 0 } };
        double inf = Double.POSITIVE_INFINITY;
        double[][] g = { { 0, 3, inf, 8, 9 }, { 3, 0, 3, 10, 5 }, { inf, 3, 0, 4, 3 }, { 8, 10, 4, 0, 20 },
                { 9, 5, 3, 20, 0 } };
        TSP2 tsp = new TSP2(g, new int[] { 1, 2, 3, 4 });
        double res = tsp.cal();
        System.out.println(res);
        List<Integer> path = tsp.path();
        System.out.println(path);
        // check
        int v = path.get(0);
        double s = 0;
        for (int i = 1; i < path.size(); i++) {
            s += g[v][path.get(i)];
            v = path.get(i);
        }
        s += g[v][path.get(0)];
        System.out.println(s);
    }
}