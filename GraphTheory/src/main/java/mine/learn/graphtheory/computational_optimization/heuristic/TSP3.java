package mine.learn.graphtheory.computational_optimization.heuristic;

import java.util.LinkedList;
import java.util.List;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;

/**
 * TSP3
 * <p>
 * 启发式
 * <p>
 * 贪心启发
 */
@Deprecated
public class TSP3 {

    private EdgeWeightedDiGraph g;
    private int V;
    private LinkedList<WeightedDirectedEdge> path;

    /**
     * 
     * @param g
     */
    public TSP3(EdgeWeightedDiGraph g) {
        this.g = g;
        V = g.V();
    }

    /**
     * 每次都找最短的节点
     * 
     * @param v
     * @return
     */
    public double greedy(int v, LinkedList<WeightedDirectedEdge> list, boolean[] vis, int visCount, int source) {
        if (visCount == V) {
            for (var e : g.adjOf(v)) {
                if (e.to() == source) {
                    list.add(e);
                    return e.weight();
                }
            }
            return Double.NEGATIVE_INFINITY;
        }
        vis[v] = true;
        double min = Double.POSITIVE_INFINITY;
        WeightedDirectedEdge w = null;
        for (WeightedDirectedEdge e : g.adjOf(v)) {
            if (!vis[e.to()]) {
                if (min > e.weight()) {
                    min = e.weight();
                    w = e;
                }
            }
        }
        list.add(w);
        return min + greedy(w.to(), list, vis, visCount + 1, source);
    }

    public double cal() {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < g.V(); i++) {
            LinkedList<WeightedDirectedEdge> list = new LinkedList<>();
            boolean[] vis = new boolean[g.V()];
            double ret = greedy(i, list, vis, 1, i);
            // System.out.println("this ret : " + ret);
            if (min > ret) {
                min = ret;
                path = list;
            }
        }
        return min;
    }

    public List<WeightedDirectedEdge> path() {
        return path;
    }

}