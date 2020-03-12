package mine.learn.graphtheory;

import java.util.ArrayList;
import java.util.List;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.util.IndexedPriorityQueue;

/**
 * Prim2
 */
public class Prim2 {

    private IndexedPriorityQueue<Double> pq;
    private EdgeWeightedGraph g;
    private double[] distTo;
    private WeightedEdge[] edgeTo;

    private void f(int v) {
        for (WeightedEdge edge : g.adjOf(v)) {
            if (edge == null)
                continue;
            int other = edge.other(v);
            double weight = edge.weight();
            if (distTo[other] < Double.POSITIVE_INFINITY)
                continue;
            if (distTo[other] > weight) {
                edgeTo[other] = edge;
                if (pq.contains(other))
                    pq.decreaseKey(other, weight);
                else
                    pq.add(other, weight);
            }
        }
    }

    public Prim2(EdgeWeightedGraph g) {
        this.g = g;
        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedEdge[vn];
        for (int i = 0; i < vn; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0;
        pq = new IndexedPriorityQueue<>(vn);
        pq.add(0, 0.);
        while (!pq.isEmpty()) {
            int minV = pq.poll();
            f(minV);
        }
    }

    public double weight() {
        Iterable<WeightedEdge> mst = mst();
        double weight = 0;
        for (WeightedEdge edge : mst) {
            weight += edge.weight();
        }
        return weight;
    }

    public Iterable<WeightedEdge> mst() {
        List<WeightedEdge> edges = new ArrayList<>(g.V() - 1);
        for (int i = 1; i < edgeTo.length; i++) {
            edges.add(edgeTo[i]);
        }
        return edges;
    }

}