
package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.util.UnionFind;

/**
 * 暂时还是单连通分量的最小生成树；
 * <p>
 * TODO 可修改为最小生成<strong>森林</strong>
 */
public class Kruskal {

    private double weight; // weight of MST
    private List<WeightedEdge> mst = new LinkedList<>(); // edges in MST

    public Kruskal(EdgeWeightedGraph g) {
        // more efficient to build heap by passing array of edges
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>(g.edges());

        UnionFind uf = new UnionFind(g.V());
        while (!pq.isEmpty() && mst.size() < g.V() - 1) {
            WeightedEdge e = pq.poll();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w); // merge v and w components
                mst.add(e); // add edge e to mst
                weight += e.weight();
            }
        }
    }

    public Iterable<WeightedEdge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

}
