package mine.learn.graphtheory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.Helpers;

/**
 * StrongComponents
 */
public class StrongComponents {

    private boolean[] marked;
    private int[] id;
    private int count;
    private ArrayList<List<Integer>> sets;
    private EdgeWeightedDiGraph g;
    private LinkedList<Integer> reversePostOrder;
    private int V;

    private void helper(EdgeWeightedDiGraph graph, int v, boolean[] vis) {
        vis[v] = true;
        for (WeightedDirectedEdge e : graph.adjOf(v)) {
            int w = e.to();
            if (!vis[w])
                helper(graph, w, vis);
        }
        reversePostOrder.push(v);
    }

    public StrongComponents(EdgeWeightedDiGraph g) {
        this.g = g;
        V = g.V();
        id = new int[V];
        marked = new boolean[V];
        // get reversePostOrder
        reversePostOrder = new LinkedList<>();
        boolean[] vis = new boolean[V];
        EdgeWeightedDiGraph reverseG = g.reverse();
        for (int i = 0; i < V; i++)
            if (!vis[i])
                helper(reverseG, i, vis);

        //
        sets = new ArrayList<>();
        for (int v : reversePostOrder) {
            if (!marked[v]) {
                dfs(v);
                count++;
            }
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        id[v] = count;
        if (count == sets.size()) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(v);
            sets.add(tmp);
        } else
            sets.get(count).add(v);
        for (WeightedDirectedEdge e : g.adjOf(v)) {
            int w = e.to();
            if (!marked[w])
                dfs(w);
        }
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

    public int id(int v) {
        return id[v];
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    /**
     * 强连通 是一个<em>等价关系</em>
     */
    public boolean stronglyConnected(int[] set) {
        if (set.length < 3)
            throw new IllegalArgumentException("集合包含元素小于等于2");
        int v = set[0];
        for (int i = 1; i < set.length - 1; i++) {
            if (id[v] != id[set[i]])
                return false;
        }
        return true;
    }

}