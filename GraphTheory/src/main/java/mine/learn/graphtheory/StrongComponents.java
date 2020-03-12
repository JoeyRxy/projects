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
            if (e != null) {
                int w = e.to();
                if (!vis[w])
                    helper(graph, w, vis);
            }
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
            if (e != null) {
                int w = e.to();
                if (!marked[w])
                    dfs(w);
            }
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

    public static void main(String[] args) throws NumberFormatException, IOException {
        EdgeWeightedDiGraph graph = (EdgeWeightedDiGraph) Helpers.getGraph("scc.txt", EdgeWeightedDiGraph.class);
        StrongComponents scc = new StrongComponents(graph);
        System.out.println(scc.count());
        for (int i = 0; i < graph.V(); i++) {
            int id = scc.id(i);
            System.out.println(i + " : " + id);
        }
        for (int i = 0; i < scc.count(); i++) {
            System.out.println(scc.allVertexOf(i));
        }
    }
}