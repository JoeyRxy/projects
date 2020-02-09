package mine.learn.graphtheory;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;

/**
 * Components
 */
public class Components {
    private EdgeWeightedGraph g;
    private int count;
    private int[] id;
    private boolean[] marked;

    public Components(EdgeWeightedGraph g) {
        this.g = g;
        int V = g.V();
        id = new int[V];
        marked = new boolean[V];
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
}