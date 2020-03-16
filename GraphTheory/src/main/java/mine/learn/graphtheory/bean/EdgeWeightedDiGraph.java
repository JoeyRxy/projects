
package mine.learn.graphtheory.bean;

import mine.learn.graphtheory.api.RealMapGraph;
import mine.learn.graphtheory.api.SymbolGraphAPI;

import java.util.*;

@SuppressWarnings("unchecked")
public class EdgeWeightedDiGraph implements SymbolGraphAPI, RealMapGraph {

    private int V; // number of vertices in this digraph
    private List<WeightedDirectedEdge>[] adj; // adj[v] = adjacency list for vertex v
    private int[] indegree; // indegree[v] = indegree of vertex v
    private List<WeightedDirectedEdge> edges;

    private String[] nameOf;
    private Map<String, Integer> indexOf;

    private Coordination[] coordinations;

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        this.indegree = new int[V];

        nameOf = new String[V];
        indexOf = new HashMap<>();
        edges = new LinkedList<>();
        adj = (List<WeightedDirectedEdge>[]) new List[V];
        coordinations = new Coordination[V];

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

    @Override
    public void setCoordinationOf(int v, Coordination coordination) {
        coordinations[v] = coordination;
    }

    @Override
    public Coordination coordinationOf(int v) {
        return coordinations[v];
    }

    @Override
    public int vertexOf(Coordination coordination) {
        for (int i = 0; i < coordinations.length; i++) {
            if (coordinations[i].equals(coordination))
                return i;
        }
        return -1;
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
    // private void validateVertex(int v) {
    // if (v < 0 || v >= V)
    // throw new IllegalArgumentException("vertex " + v + " is not between 0 and " +
    // (V - 1));
    // }

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
        // validateVertex(v);
        return nameOf[v];
    }

    @Override
    public void setIndexOf(String vertex, int index) {
        // validateVertex(index);
        indexOf.put(vertex, index);
        nameOf[index] = vertex;
    }

    @Override
    public int indexOf(String vertex) {
        return indexOf.get(vertex);
    }

    @Override
    public double dist(int v, int w) {
        return coordinations[v].dist(coordinations[w]);
    }

}