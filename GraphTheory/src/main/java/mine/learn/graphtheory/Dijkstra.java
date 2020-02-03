package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.Objects;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.PriorityQueueM;

public class Dijkstra {
    private double[] distTo; // distTo[v] = distance of shortest s->v path
    private WeightedDirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
    private PriorityQueueM<Pair> pq; // priority queue of vertices

    class Pair implements Comparable<Pair> {
        Integer vertex;

        /**
         * distance from {@code source} to this {@link Pair}
         */
        Double dist;

        public Pair(Integer vertex, Double dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(Pair o) {
            return dist.compareTo(o.dist);
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
            return Objects.equals(vertex, other.vertex);
        }

        private Dijkstra getEnclosingInstance() {
            return Dijkstra.this;
        }

        /**
         * IMPORTANT
         * equals方法是针对vertex这个属性，而不是dist这个属性，这是为了在PriorityQueue中进行更换值的操作。这是将优先队列变为一种Map的关键
         */

    }

    public Dijkstra(EdgeWeightedDiGraph g, int s) {
        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedDirectedEdge[vn];

        validateVertex(s);

        for (int v = 0; v < vn; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new PriorityQueueM<>(vn);
        pq.add(new Pair(s, distTo[s]));
        while (!pq.isEmpty()) {
            int v = pq.poll().vertex;
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
            Pair tmp = new Pair(w, distTo[w]);
            if (pq.contains(tmp)) {
                pq.remove(tmp);
            }
            pq.add(tmp);
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