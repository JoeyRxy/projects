package mine.learn.graphtheory;

import java.util.Iterator;
import java.util.LinkedList;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.util.IndexedPriorityQueue;

public class DijkstraUndirectedGraph {
    private double[] distTo; // distTo[v] = distance of shortest s->v path
    private WeightedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
    private IndexedPriorityQueue<Double> pq; // priority queue of vertices
    private int s;

    public DijkstraUndirectedGraph(EdgeWeightedGraph g, int s) {
        for (WeightedEdge e : g.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedEdge[vn];

        validateVertex(s);

        for (int v = 0; v < vn; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexedPriorityQueue<>(vn);
        pq.add(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            for (WeightedEdge e : g.adjOf(v))
                if (e != null)
                    relax(e, v);
        }
    }

    // relax edge e and update pq if changed
    private void relax(WeightedEdge e, int v) {
        int w = e.other(v);
        double d = distTo[v] + e.weight();
        if (distTo[w] > d) {
            distTo[w] = d;
            edgeTo[w] = e;
            pq.add(w, d);
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

    public Iterable<WeightedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        LinkedList<WeightedEdge> path = new LinkedList<>();
        int x = v;
        for (WeightedEdge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public String stringPathTo(int v) {
        Iterable<WeightedEdge> path = pathTo(v);
        if (path == null)
            return "Infinite : " + v;
        Iterator<WeightedEdge> iter = path.iterator();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-7.3f : ", distTo[v])).append(s);
        int i = s;
        while (iter.hasNext()) {
            i = iter.next().other(i);
            builder.append(" -- ").append(i);
        }
        return builder.toString();
    }
}