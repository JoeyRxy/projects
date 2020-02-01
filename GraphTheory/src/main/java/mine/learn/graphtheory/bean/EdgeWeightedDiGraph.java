
package mine.learn.graphtheory.bean;

import java.util.HashSet;
import java.util.List;

import mine.learn.graphtheory.api.SymbolGraphAPI;

import java.util.*;

@SuppressWarnings("unchecked")
public class EdgeWeightedDiGraph implements SymbolGraphAPI {

    private int V; // number of vertices in this digraph
    private Set<WeightedDirectedEdge>[] adj; // adj[v] = adjacency list for vertex v
    private int[] indegree; // indegree[v] = indegree of vertex v
    private List<WeightedDirectedEdge> edges;

    private String[] nameOf;
    private Map<String, Integer> indexOf;

    public EdgeWeightedDiGraph(int V) {
        if (V <= 0)
            throw new IllegalArgumentException("节点数必须为正整数");
        this.V = V;
        this.indegree = new int[V];
        edges = new LinkedList<>();
        adj = (Set<WeightedDirectedEdge>[]) new Set[V];
        for (int v = 0; v < V; v++)
            adj[v] = new HashSet<>();

    }

    public List<WeightedDirectedEdge> edges() {
        return edges;
    }

    public int V() {
        return V;
    }

    public int E() {
        return edges.size();
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(WeightedDirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        edges.add(e);
    }

    public Iterable<WeightedDirectedEdge> adjOf(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < V; vertex++) {
            builder.append(vertex + " :\n");
            for (WeightedDirectedEdge edge : adj[vertex])
                builder.append("\t").append(edge).append("\n");
        }
        return builder.toString();
    }

    /**
     * O(VE)
     */
    public EdgeWeightedDiGraph reverse() {
        EdgeWeightedDiGraph reverseGraph = new EdgeWeightedDiGraph(V);
        reverseGraph.indexOf = indexOf;
        for (int v = 0; v < V; v++) {
            for (WeightedDirectedEdge e : adj[v]) {
                reverseGraph.addEdge(new WeightedDirectedEdge(e.to(), e.from(), e.weight()));
            }
        }
        return reverseGraph;
    }

    @Override
    public String nameOf(int v) {
        validateVertex(v);
        return nameOf[v];
    }

    @Override
    public void setIndexOf(String vertex, int index) {
        validateVertex(index);
        indexOf.put(vertex, index);
        nameOf[index] = vertex;
    }

    @Override
    public int indexOf(String vertex) {
        return indexOf.get(vertex);
    }

}