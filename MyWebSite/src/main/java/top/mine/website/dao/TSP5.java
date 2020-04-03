package top.mine.website.dao;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import top.mine.website.dao.tsputil.EdgeWeightedDiGraph;
import top.mine.website.dao.tsputil.FloydWarshall;
import top.mine.website.dao.tsputil.WeightedDirectedEdge;

/**
 * TSP5
 * <p>
 * 模拟退火
 */
public class TSP5 {
    private int V;
    private double[][] g;
    private int[] bestOrder;
    private double bestDist;
    private FloydWarshall floydwarshall;
    private int[] set;
    private double p;
    private Random r;
    private List<Integer> xAxisData;
    private List<Double> series1Data;

    /**
     * 
     * @param graph
     * @param set
     * @param t0     初始温度
     * @param tf     结束温度
     * @param a      温度衰减率
     * @param markov 每轮进行多少次
     * @param p      越接近1，块交换越多；越接近0，点交换越多
     */
    public TSP5(EdgeWeightedDiGraph graph, int[] set, double t0, double tf, double a, int markov, double p) {
        this.p = p;
        if (a < 0 || a > 1)
            throw new IllegalArgumentException("温度衰减0<a<1");
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("变异概率0<p<1");
        if (t0 <= tf)
            throw new IllegalArgumentException("初始温度应高于最终温度");
        floydwarshall = new FloydWarshall(graph);
        graph = null;
        this.V = set.length;
        this.set = set;
        this.g = new double[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                g[i][j] = floydwarshall.dist(set[i], set[j]);
            }
        }
        int[] order = getGreedyOrder();
        double dist = calcDist(order);
        System.out.println("初始的贪心顺序的距离：" + dist);
        bestOrder = Arrays.copyOf(order, V);
        bestDist = dist;
        int[] order_new = Arrays.copyOf(order, order.length);
        double dist_new = dist;
        // 退火
        int count = 0;
        r = new Random();
        xAxisData = new LinkedList<>();
        series1Data = new LinkedList<>();
        r.setSeed(System.currentTimeMillis());
        for (double t = t0; t >= tf; t *= a) {
            xAxisData.add(count);
            series1Data.add(bestDist);
            count++;
            // System.out.println("第" + (count) + "轮 : " + bestDist);
            for (int i = 0; i < markov; i++) {
                genNewOrder(order_new);
                dist_new = calcDist(order_new);
                if (dist_new < dist) {
                    dist = dist_new;
                    order = order_new;
                    if (dist_new < bestDist) {
                        bestDist = dist_new;
                        bestOrder = Arrays.copyOf(order_new, V);
                    }
                } else if (Math.random() < Math.exp((dist - dist_new) / t)) { // 即使不比原来短，有概率也会更新
                    dist = dist_new;
                    order = order_new;
                }
                order_new = Arrays.copyOf(order, V);
            }
        }
        System.out.println("共进行了" + count + "轮.");
    }

    private int[] getGreedyOrder() {
        double min = Double.POSITIVE_INFINITY;
        int[] best_order = null;
        for (int i = 0; i < V; i++) {
            int[] order = new int[V];
            boolean[] vis = new boolean[V];
            double ret = greedy(i, order, vis, 0);
            // System.out.println("this ret : " + ret);
            if (min > ret) {
                min = ret;
                best_order = order;
            }
        }
        rotateTo0(best_order);
        return best_order;
    }

    private double greedy(int v, int[] order, boolean[] vis, int depth) {
        if (depth == V - 1) {
            order[depth] = v;
            return g[v][order[0]];
        }
        vis[v] = true;
        order[depth] = v;
        double[] adj = g[v];
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (int i = 0; i < adj.length; i++) {
            if (!vis[i]) {
                if (adj[i] < min) {
                    min = adj[i];
                    w = i;
                }
            }
        }
        return min + greedy(w, order, vis, depth + 1);
    }

    private void rotateTo0(int[] best_order) {
        int tmp;
        while (best_order[0] != 0) {
            tmp = best_order[0];
            for (int i = 0; i < best_order.length - 1; i++) {
                best_order[i] = best_order[i + 1];
            }
            best_order[best_order.length - 1] = tmp;
        }
    }

    private void swap(int[] order, int i, int j, int k) {
        int[] tmp = Arrays.copyOfRange(order, i, j);
        while (j < k)
            order[i++] = order[j++];
        j = 0;
        while (i < k)
            order[i++] = tmp[j++];
    }

    private void sort3(int[] seg) {
        int j = seg[1] < seg[2] ? 1 : 2;
        int t;
        if (seg[0] > seg[j]) {
            t = seg[0];
            seg[0] = seg[j];
            seg[j] = t;
        }
        if (seg[1] > seg[2]) {
            t = seg[1];
            seg[1] = seg[2];
            seg[2] = t;
        }
    }

    private void genNewOrder(int[] order) {
        int[] seg = new int[3];
        seg[0] = r.nextInt(V);
        seg[1] = r.nextInt(V);
        while (seg[1] == seg[0]) {
            seg[1] = r.nextInt(V);
        }
        if (Math.random() < p) {// 交换两点
            swap(order, seg[0], seg[1]);
        } else {// 交换两段
            seg[2] = r.nextInt(V);
            sort3(seg);
            swap(order, seg[0], seg[1], seg[2]);
        }
    }

    private void swap(int[] order, int v, int w) {
        int t = order[v];
        order[v] = order[w];
        order[w] = t;
    }

    private double calcDist(int[] order) {
        double sum = 0;
        for (int i = 0; i < V - 1; i++) {
            sum += g[order[i]][order[i + 1]];
        }
        sum += g[order[V - 1]][order[0]];
        return sum;
    }

    public double getBestDist() {
        return bestDist;
    }

    public List<WeightedDirectedEdge> getPath() {
        rotateTo0(bestOrder);
        List<WeightedDirectedEdge> path = new LinkedList<>();
        for (int i = 0; i < V - 1; i++) {
            path.addAll(floydwarshall.path(set[bestOrder[i]], set[bestOrder[i + 1]]));
        }
        path.addAll(floydwarshall.path(set[bestOrder[V - 1]], set[bestOrder[0]]));
        return path;
    }

    public List<Integer> getXAxisData() {
        return xAxisData;
    }

    public List<Double> getSeries1Data() {
        return series1Data;
    }
}