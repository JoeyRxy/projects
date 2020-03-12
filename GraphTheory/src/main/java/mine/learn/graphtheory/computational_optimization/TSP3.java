package mine.learn.graphtheory.computational_optimization;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;

/**
 * TSP3
 * <p>
 * 启发式
 * <p>
 * 贪心启发
 */
public class TSP3 {

    private EdgeWeightedDiGraph g;
    private int V;
    private LinkedList<Integer> pathList;
    private LinkedList<Double> pathLenF;

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
    public double greedy(int v, LinkedList<Integer> list, LinkedList<Double> pathLen, boolean[] vis) {
        if (V == list.size())
            return 0;
        list.add(v);
        vis[v] = true;
        double min = Double.POSITIVE_INFINITY;
        int idx = 0;
        for (WeightedDirectedEdge w : g.adjOf(v)) {
            if (w != null && vis[w.to()] && w.to() != v) {
                if (min > w.weight()) {
                    min = w.weight();
                    idx = w.to();
                }
            }
        }
        pathLen.add(min);
        return min + greedy(idx, list, pathLen, vis);
    }

    public void cal() {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < g.V(); i++) {
            LinkedList<Integer> list = new LinkedList<>();
            LinkedList<Double> pathLen = new LinkedList<>();
            boolean[] vis = new boolean[g.V()];
            double ret = greedy(i, list, pathLen, vis);
            if (min > ret) {
                min = ret;
                pathList = list;
                pathLenF = pathLen;
            }
        }
    }

    public List<Integer> path() {
        return pathList;
    }

    public List<Double> pathLen() {
        return pathLenF;
    }

    public static void main(String[] args) {
        double inf = Double.POSITIVE_INFINITY;
        double[][] g = { { 0, 3, inf, 8, 9 }, { 3, 0, 3, 10, 5 }, { inf, 3, 0, 4, 3 }, { 8, 10, 4, 0, 20 },
                { 9, 5, 3, 20, 0 } };
        TSP3 tsp = new TSP3(new EdgeWeightedDiGraph(g));
    }
}