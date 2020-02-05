
package mine.learn.graphtheory.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mine.learn.graphtheory.api.SymbolGraphAPI;

@SuppressWarnings("unchecked")
public class EdgeWeightedGraph implements SymbolGraphAPI {

    private int V;
    private Set<WeightedEdge>[] adj;
    private Set<WeightedEdge> edges;

    private String[] nameOf;
    private Map<String, Integer> indexOf;

    public EdgeWeightedGraph(int V) {
        if (V <= 0)
            throw new IllegalArgumentException("节点数必须为正整数");
        this.V = V;
        nameOf = new String[V];
        indexOf = new HashMap<>();
        adj = (Set<WeightedEdge>[]) new Set[V];
        edges = new HashSet<>();
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
        if (edges.contains(e))
            return;
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
    }

    public Set<WeightedEdge> edges() {
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

    @Override
    public int indexOf(String vertex) {
        return indexOf.get(vertex);
    }

    @Override
    public String nameOf(int v) {
        return nameOf[v];
    }

    @Override
    public void setIndexOf(String vertex, int index) {
        validateVertex(index);
        nameOf[index] = vertex;
        indexOf.put(vertex, index);
    }
}
