package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.PriorityQueue;

import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.bean.EdgeWeightedGraph;

public class DijkstraUndirectedGraph {
    private double[] distTo; // distTo[v] = distance of shortest s->v path
    private WeightedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
    private PriorityQueue<Pair> pq; // priority queue of vertices

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
            result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
            return result;
        }

        /**
         * IMPORTANT
         * equals方法是针对vertex这个属性，而不是dist这个属性，这是为了在PriorityQueue中进行更换值的操作。这是将优先队列变为一种Map的关键
         */
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
            if (vertex == null) {
                if (other.vertex != null)
                    return false;
            } else if (!vertex.equals(other.vertex))
                return false;
            return true;
        }

        private DijkstraUndirectedGraph getEnclosingInstance() {
            return DijkstraUndirectedGraph.this;
        }

    }

    public DijkstraUndirectedGraph(EdgeWeightedGraph g, int s) {
        for (WeightedEdge e : g.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        int vn = g.V();
        distTo = new double[vn];
        edgeTo = new WeightedEdge[vn];

        validateVertex(s);

        for (int v = 0; v < v; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new PriorityQueue<>(vn);
        pq.add(new Pair(s, distTo[s]));
        while (!pq.isEmpty()) {
            int v = pq.poll().vertex;
            for (WeightedEdge e : g.adj(v))
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

            Pair tmp = new Pair(w, distTo[w]);

            if (pq.contains(tmp)) {
                pq.remove(tmp);
                pq.add(tmp);
            } else
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
        LinkedList<WeightedEdge> real = new LinkedList<>();
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