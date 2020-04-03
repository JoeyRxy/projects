package top.mine.website.dao.tsputil;

import java.util.*;

@SuppressWarnings("unchecked")
public class EdgeWeightedDiGraph {

    private int V; // number of vertices in this digraph
    private List<WeightedDirectedEdge>[] adj; // adj[v] = adjacency list for vertex v
    private int[] indegree; // indegree[v] = indegree of vertex v
    private List<WeightedDirectedEdge> edges;

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        this.indegree = new int[V];

        edges = new LinkedList<>();
        adj = (List<WeightedDirectedEdge>[]) new List[V];

        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<>();

    }

    public EdgeWeightedDiGraph(double[][] g) {
        this(g.length);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (g[i][j] != Double.POSITIVE_INFINITY && j != i) {
                    addEdge(new WeightedDirectedEdge(i, j, g[i][j]));
                }
            }
        }
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

    public void changeEdge(WeightedDirectedEdge e) {
        int v = e.from();
        adj[v].remove(e);
        adj[v].add(e);
    }

    /**
     * 既是添加
     * 
     * @param e
     */
    public void addEdge(WeightedDirectedEdge e) {
        int v = e.from();
        int w = e.to();
        // validateVertex(v);
        // validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        edges.add(e);
    }

    public Iterable<WeightedDirectedEdge> adjOf(int v) {
        // validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        // validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        // validateVertex(v);
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
        for (int v = 0; v < V; v++) {
            for (WeightedDirectedEdge e : adj[v]) {
                reverseGraph.addEdge(new WeightedDirectedEdge(e.to(), e.from(), e.weight()));
            }
        }
        return reverseGraph;
    }

}