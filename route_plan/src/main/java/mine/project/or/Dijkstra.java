package mine.project.or;

import java.util.*;

import mine.project.bean.DirectedEdge;
import mine.project.bean.EdgeWeightedDiGraph;

/**
 * Dijkstra
 * <p>
 * Vertex {@code source} to {@code v}
 */
public class Dijkstra {

    private String source;
    private EdgeWeightedDiGraph g;
    /**
     * vertex -> double : distance from {@code source} to {@code vertex}
     */
    private Map<String, Double> distTo;

    /**
     * records the shortest path-chain from {@code source} to {@code v}
     */
    private Map<String, DirectedEdge> edgeMap;

    PriorityQueue<Pair> pq;

    /**
     * pair(vertex, distTo[vertex])
     */
    class Pair implements Comparator<Pair> {
        String vertex;

        /**
         * distance from {@code source} to this {@link Pair}
         */
        Double dist;

        @Override
        public int compare(Pair o1, Pair o2) {
            double x = o1.dist - o2.dist;
            if (x < 0)
                return -1;
            else if (x > 0)
                return 1;
            else
                return 0;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
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
            if (vertex == null) {
                if (other.vertex != null)
                    return false;
            } else if (!vertex.equals(other.vertex))
                return false;
            return true;
        }

        private Dijkstra getEnclosingInstance() {
            return Dijkstra.this;
        }

        public Pair(String vertex, Double dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

    }

    public Dijkstra(EdgeWeightedDiGraph g, String source) {
        this.source = source;
        this.g = g;
        distTo = new HashMap<>();
        edgeMap = new HashMap<>();
        pq = new PriorityQueue<>();
        for (String v : g.vertexSet())
            distTo.put(v, Double.POSITIVE_INFINITY);
        distTo.put(source, 0.0);

        pq.add(new Pair(source, 0.0));
        while (!pq.isEmpty()) {
            Pair min = pq.poll();
            relax(min.vertex);
        }
    }

    public boolean hasPathTo(String v) {
        return distTo.get(v) < Double.POSITIVE_INFINITY;
    }

    public double distTo(String v) {
        return distTo.get(v);
    }

    public Iterable<DirectedEdge> pathTo(String v) {
        if (!hasPathTo(v))
            return null;
        List<DirectedEdge> list = new LinkedList<>();
        for (DirectedEdge e = edgeMap.get(source); e != null; e = edgeMap.get(e.to()))
            list.add(e);

        return list;
    }

    /**
     * {@code O(n)}
     * 
     * @param from
     */
    public void relax(String from) {
        for (DirectedEdge e : g.adjOf(from)) {
            String to = e.to();
            double d = distTo(from) + e.weight();
            if (distTo(to) > d) {
                distTo.put(to, d);
                edgeMap.put(from, e);
            }
        }
    }

}