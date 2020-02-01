
package mine.learn.graphtheory.bean;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EdgeWeightedGraph {

    private int V;
    private Set<WeightedEdge>[] adj;
    private List<WeightedEdge> edges;

    public EdgeWeightedGraph(int V) {
        if (V <= 0)
            throw new IllegalArgumentException("节点数必须为正整数");
        this.V = V;
        adj = (Set<WeightedEdge>[]) new Set[V];
        edges = new LinkedList<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new HashSet<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return edges.size();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(WeightedEdge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
    }

    public List<WeightedEdge> edges() {
        return edges;
    }

    public Iterable<WeightedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < V; vertex++) {
            builder.append(vertex + " :\n");
            for (WeightedEdge edge : adj[vertex])
                builder.append("\t").append(edge).append("\n");
        }
        return builder.toString();
    }
}
