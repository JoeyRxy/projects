package mine.learn.graphtheory;

import java.util.*;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.util.PriorityQueueM;

/**
 * Prim
 */
public class Prim {

    private PriorityQueueM<Pair> pq;
    private EdgeWeightedGraph g;
    /**
     * distTo[v] : 节点到树的最短距离
     */
    private double[] distTo;
    /** edge connected to the vertex */
    private WeightedEdge[] edgeTo;

    class Pair implements Comparable<Pair> {
        int vertex;
        Double distTo;

        @Override
        public int compareTo(Pair o) {
            return distTo.compareTo(o.distTo);
        }

        public Pair(int vertex, double distTo) {
            this.vertex = vertex;
            this.distTo = distTo;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + Objects.hash(vertex);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            return vertex == other.vertex;
        }

        private Prim getEnclosingInstance() {
            return Prim.this;
        }

    }

    public void f(int v) {
        for (WeightedEdge e : g.adj(v)) {
            int other = e.other(v);
            Pair pair = new Pair(other, e.weight());
            if (!marked(other)) {
                pq.add(pair);
            } else if (distTo[other] > e.weight()) {
                // pq.remove(pair);
                pq.add(pair);
                edgeTo[other] = e;
            }
        }
    }

    private boolean marked(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Prim(EdgeWeightedGraph g) {
        this.g = g;
        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedEdge[vn];
        for (int i = 0; i < vn; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0;
        pq = new PriorityQueueM<>(vn);
        f(0);
        while (!pq.isEmpty()) {
            Pair min = pq.poll();
            f(min.vertex);
        }
    }
}