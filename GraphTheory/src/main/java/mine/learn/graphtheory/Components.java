package mine.learn.graphtheory;

import java.io.IOException;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.util.Helpers;

/**
 * Components
 */
public class Components {
    private EdgeWeightedGraph g;
    private int count;
    private int[] id;
    private boolean[] marked;
    private int[] size;

    public Components(EdgeWeightedGraph g) {
        this.g = g;
        int V = g.V();
        id = new int[V];
        marked = new boolean[V];
        size = new int[V];
        for (int i = 0; i < V; i++) {
            if (!marked[i]) {
                count++;
                dfs(i);
            }
        }
    }

    private void dfs(int v) {
        id[v] = count;
        marked[v] = true;
        size[count]++;
        for (WeightedEdge e : g.adj(v)) {
            int other = e.other(v);
            if (!marked[other])
                dfs(other);
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

    public int sizeOf(int id) {
        return size[id];
    }

    public EdgeWeightedGraph[] inducedSubGraphs() {
        EdgeWeightedGraph[] subGraphs = new EdgeWeightedGraph[count];
        for (int i = 0; i < count; i++) {
            subGraphs[i] = new EdgeWeightedGraph(sizeOf(i));
        }
        return null;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        Components cc = new Components((EdgeWeightedGraph) Helpers.getGraph("mediumEWG.txt", EdgeWeightedGraph.class));
        int[] id = cc.id;
        for (int i = 0; i < id.length; i++) {
            System.out.println(i + " : " + id[i]);
        }
    }
}