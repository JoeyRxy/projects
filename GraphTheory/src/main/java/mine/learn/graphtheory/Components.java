package mine.learn.graphtheory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<List<Integer>> sets;

    public Components(EdgeWeightedGraph g) {
        this.g = g;
        int V = g.V();
        id = new int[V];
        sets = new ArrayList<>();
        marked = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!marked[i]) {
                dfs(i);
                count++;
            }
        }
    }

    private void dfs(int v) {
        id[v] = count;
        if (count == sets.size()) {
            List<Integer> _t = new ArrayList<>();
            _t.add(v);
            sets.add(_t);
        } else {
            sets.get(count).add(v);
        }
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

    public List<Integer> allVertexOf(int id) {
        return sets.get(id);
    }

    public int sizeOf(int id) {
        return sets.get(id).size();
    }

    @Deprecated
    public EdgeWeightedGraph[] inducedSubGraphs() {
        EdgeWeightedGraph[] subGraphs = new EdgeWeightedGraph[count];
        for (int i = 0; i < count; i++) {
            subGraphs[i] = new EdgeWeightedGraph(sizeOf(i));
        }
        return null;
    }
}