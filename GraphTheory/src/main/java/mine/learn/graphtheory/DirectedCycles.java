package mine.learn.graphtheory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * DirectedCycle
 * <p>
 * 
 */
public class DirectedCycles {

    private boolean[] marked;
    private boolean[] isCycle;
    private List<Stack<Integer>> cycles;
    private EdgeWeightedDiGraph g;
    private int[] edgeTo;

    /** help to reverse path-stack */
    Stack<Integer> helper = new Stack<>();

    public DirectedCycles(EdgeWeightedDiGraph g) {
        this.g = g;
        int numV = g.V();
        marked = new boolean[numV];
        edgeTo = new int[numV];
        isCycle = new boolean[numV];
        cycles = new LinkedList<>();
        for (int v = 0; v < numV; v++) {
            dfs(v);
        }
    }

    public boolean hasCycle() {
        return cycles.size() != 0;
    }

    public List<Stack<Integer>> getAllCycles() {
        return cycles;
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        isCycle[vertex] = true;
        for (WeightedDirectedEdge e : g.adjOf(vertex)) {
            if (e == null)
                continue;
            int to = e.to();
            if (!marked[to]) {
                edgeTo[to] = vertex;
                dfs(to);
            } else if (isCycle[to]) {
                Stack<Integer> path = new Stack<>();
                helper.push(to);
                helper.push(vertex);
                int from = edgeTo[vertex];
                while (from != to) {
                    helper.push(from);
                    from = edgeTo[from];
                }
                while (!helper.isEmpty())
                    path.push(helper.pop());

                cycles.add(path);
            }
        }
        isCycle[vertex] = false; // 重要！回退一步之后的marked为ture但is为false，防止误认为是环！
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (hasCycle())
            for (Stack<Integer> cycle : cycles) {
                Iterator<Integer> iter = cycle.iterator();
                if (iter.hasNext())
                    builder.append(iter.next());
                while (iter.hasNext()) {
                    builder.append(" ==> " + iter.next());
                }
                builder.append("\n");
            }
        return builder.toString();
    }
}