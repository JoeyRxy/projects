package mine.project.or;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import mine.project.bean.DirectedEdge;
import mine.project.bean.EdgeWeightedDiGraph;

/**
 * Dijkstra
 * <p>
 * Vertex {@code source} to {@code v}
 * <p>
 * 不保证无环
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
    private Map<String, DirectedEdge> edgeTo;

    PriorityQueue<Pair> pq;

    /**
     * pair(vertex, distTo[vertex])
     * <p>
     * TODO 感觉将Pair变成Map之后，还有必要存在
     * <p>
     * private Map<String, Double> distTo;
     * <p>
     * 属性吗？
     */
    class Pair implements Comparable<Pair> {
        String vertex;

        /**
         * distance from {@code source} to this {@link Pair}
         */
        Double dist;

        public Pair(String vertex, Double dist) {
            this.vertex = vertex;
            this.dist = dist;
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

        private Dijkstra getEnclosingInstance() {
            return Dijkstra.this;
        }

        @Override
        public int compareTo(Pair o) {
            return dist.compareTo(o.dist);
        }

    }

    public Dijkstra(EdgeWeightedDiGraph g, String source) {
        this.source = source;
        this.g = g;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        // IMPORTANT
        // 不加comparator而直接让其中的类继承Comparator接口（或Comparable接口）能不能自动调用其中的compare(compareTo)方法进行排序？
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

    public Stack<DirectedEdge> pathTo(String v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> list = new Stack<>();
        for (DirectedEdge e = edgeTo.get(v); e != null; e = edgeTo.get(e.from()))
            list.add(e);
        return list;
    }

    public String PathPrinter(String v) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-10.5f", distTo(v))).append(" : ");
        builder.append(source);
        Stack<DirectedEdge> pathTo = pathTo(v);
        if (pathTo != null)
            while (!pathTo.isEmpty())
                builder.append(" ==> " + pathTo.pop().to());
        else
            builder.append(" ==> ").append(v);

        return builder.toString();
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
                edgeTo.put(to, e);
                // IMPORTANT 直接add能不能够获得change的效果？不能，因为PriorityQueue是允许重复元素的！
                Pair tmp = new Pair(to, distTo(to));
                if (pq.contains(tmp)) {
                    pq.remove(tmp);
                    pq.add(tmp);
                } else
                    pq.add(tmp);
            }
        }
    }
}