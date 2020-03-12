
package mine.learn.graphtheory.bean;

import mine.learn.graphtheory.api.RealMapGraph;
import mine.learn.graphtheory.api.SymbolGraphAPI;

import java.util.*;

/**
 * 改用邻接矩阵表示
 * <p>
 * 
 */
public class EdgeWeightedDiGraph implements SymbolGraphAPI, RealMapGraph {

    private WeightedDirectedEdge[][] g;

    private int V; // number of vertices in this digraph
    private int[] indegree; // indegree[v] = indegree of vertex v
    private List<WeightedDirectedEdge> edges;

    private String[] nameOf;
    private Map<String, Integer> indexOf;

    private Coordination[] coordinations;

    /**
     * TODO FOR TEST;DELETE IN Std VERSION
     * 
     * @param g
     */
    public EdgeWeightedDiGraph(double[][] g) {
        V = g.length;
        this.g = new WeightedDirectedEdge[g.length][g.length];
        edges = new LinkedList<>();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (!Double.isInfinite(g[i][j])) {
                    this.g[i][j] = new WeightedDirectedEdge(i, j, g[i][j]);
                    edges.add(this.g[i][j]);
                }
            }
        }
    }

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        this.indegree = new int[V];

        nameOf = new String[V];
        indexOf = new HashMap<>();
        edges = new LinkedList<>();
        g = new WeightedDirectedEdge[V][V];
        for (int i = 0; i < V; i++) {
            g[i][i] = new WeightedDirectedEdge(i, i, 0);
        }
        coordinations = new Coordination[V];
    }

    public double wij(int i, int j) {
        if (g[i][j] == null)
            return Double.POSITIVE_INFINITY;
        return g[i][j].weight();
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

    public void addEdge(WeightedDirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (v == w)
            return;
        // //validateVertex(v);
        // //validateVertex(w);
        g[v][w] = e;
        indegree[w]++;
        edges.add(e);
    }

    public Iterable<WeightedDirectedEdge> adjOf(int v) {
        // validateVertex(v);
        ArrayList<WeightedDirectedEdge> list = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            if()
        }
    }

    // public int outdegree(int v) {
    // //validateVertex(v);
    // return adj[v].size();
    // }

    public int indegree(int v) {
        // validateVertex(v);
        return indegree[v];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < V; vertex++) {
            builder.append(vertex + " :\n");
            for (WeightedDirectedEdge edge : g[vertex])
                if (edge != null)
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
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                reverseGraph.g[i][j] = new WeightedDirectedEdge(g[j][i]);
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