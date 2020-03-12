
package mine.learn.graphtheory.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mine.learn.graphtheory.api.RealMapGraph;
import mine.learn.graphtheory.api.SymbolGraphAPI;

public class EdgeWeightedGraph implements SymbolGraphAPI, RealMapGraph {

    private int V;
    /**
     * IMPORTANT：妙啊！实际上看似浪费空间，实际上更节省空间！null不浪费空间啊！
     * <p>
     * 只取上半部
     */
    private WeightedEdge[][] g;
    private Set<WeightedEdge> edges;

    private String[] nameOf;
    private Map<String, Integer> indexOf;

    private Coordination[] coordinations;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        nameOf = new String[V];
        indexOf = new HashMap<>();
        g = new WeightedEdge[V][V];
        for (int i = 0; i < V; i++) {
            g[i][i] = new WeightedEdge(i, i, 0);
        }
        edges = new HashSet<>();
        coordinations = new Coordination[V];
    }

    public double wij(int i, int j) {
        if (g[i][j] == null)
            return Double.POSITIVE_INFINITY;
        if (i > j)
            return g[i][j].weight();
        else
            return g[j][i].weight();
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

    public int V() {
        return V;
    }

    public int E() {
        return edges.size();
    }

    public void addEdge(WeightedEdge e) {
        if (edges.contains(e))
            return;
        int v = e.either();
        int w = e.other(v);
        if (v > w) {
            g[v][w] = e;
        } else if (v < w)
            g[w][v] = e;
        else
            return;
        // validateVertex(v);
        // validateVertex(w);
        edges.add(e);
    }

    public Set<WeightedEdge> edges() {
        return edges;
    }

    public WeightedEdge[] adjOf(int v) {
        //// validateVertex(v);
        return g[v];
    }

    // public int degree(int v) {
    // // validateVertex(v);
    // return g[v].size();
    // }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int vertex = 0; vertex < V; vertex++) {
            builder.append(vertex + " :\n");
            for (int i = 0; i < vertex; i++) {
                if (g[vertex][i] != null)
                    builder.append("\t").append(g[vertex][i]).append("\n");
            }
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
        // validateVertex(index);
        nameOf[index] = vertex;
        indexOf.put(vertex, index);
    }

    @Override
    public double dist(int v, int w) {
        return coordinations[v].dist(coordinations[w]);
    }
}
