package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.Objects;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.IndexedPriorityQueue;

public class Dijkstra {
    private double[] distTo; // distTo[v] = distance of shortest s->v path
    private WeightedDirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
    // private PriorityQueueM<Pair> pq; // priority queue of vertices
    private IndexedPriorityQueue<Double> pq;

    public Dijkstra(EdgeWeightedDiGraph g, int s) {
        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedDirectedEdge[vn];

        validateVertex(s);

        for (int v = 0; v < vn; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        // pq = new PriorityQueueM<>(vn);
        pq = new IndexedPriorityQueue<>(vn);
        pq.add(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            for (WeightedDirectedEdge e : g.adjOf(v))
                relax(e);
        }
    }

    // relax edge e and update pq if changed
    private void relax(WeightedDirectedEdge e) {
        int v = e.from(), w = e.to();
        double d = distTo[v] + e.weight();
        if (distTo[w] > d) {
            distTo[w] = d;
            edgeTo[w] = e;
            // IMPORTANT 直接add能不能够获得change的效果？不能，因为PriorityQueue是允许重复元素的！
            // Pair tmp = new Pair(w, distTo[w]);
            pq.add(w, distTo[w]);// add自动有changeKey的作用
        }
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<WeightedDirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        LinkedList<WeightedDirectedEdge> path = new LinkedList<>();
        for (WeightedDirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        LinkedList<WeightedDirectedEdge> real = new LinkedList<>();
        while (!path.isEmpty())
            real.push(path.pop());
        return real;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}