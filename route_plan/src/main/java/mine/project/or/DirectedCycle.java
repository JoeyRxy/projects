package mine.project.or;

import java.util.*;

import mine.project.bean.DirectedEdge;
import mine.project.bean.EdgeWeightedDiGraph;

/**
 * DirectedCycle
 * <p>
 * 
 */
public class DirectedCycle {

    private HashMap<String, Boolean> marked;
    private Map<String, Boolean> isCycle;
    private List<Stack<String>> cycles;
    private EdgeWeightedDiGraph g;
    private Map<String, DirectedEdge> edgeTo;

    /** help to reverse path-stack */
    Stack<String> helper = new Stack<>();

    public DirectedCycle(EdgeWeightedDiGraph g) {
        this.g = g;
        marked = new HashMap<>();
        edgeTo = new HashMap<>();
        isCycle = new HashMap<>();
        cycles = new LinkedList<>();
        Set<String> vertexSet = g.vertexSet();
        for (String v : vertexSet) {
            marked.put(v, false);
            isCycle.put(v, false);
        }
        for (String v : vertexSet) {
            dfs(v);
        }
    }

    public boolean hasCycle() {
        return cycles.size() != 0;
    }

    public List<Stack<String>> getAllCycles() {
        return cycles;
    }

    private void dfs(String vertex) {
        marked.put(vertex, true);
        isCycle.put(vertex, true);
        for (DirectedEdge e : g.adjOf(vertex)) {
            String to = e.to();
            if (!marked.get(to)) {
                edgeTo.put(to, e);
                dfs(to);
            } else if (isCycle.get(to)) {
                Stack<String> path = new Stack<>();
                helper.push(to);
                helper.push(vertex);
                String from = edgeTo.get(vertex).from();
                while (!from.equals(to)) {
                    helper.push(from);
                    from = edgeTo.get(from).from();
                }
                while (!helper.isEmpty()) {
                    path.push(helper.pop());
                }
                cycles.add(path);
            }
        }
        isCycle.put(vertex, false);// 重要！回退一步之后的marked为ture但is为false，防止误认为是环！
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (hasCycle())
            for (Stack<String> cycle : cycles) {
                Iterator<String> iter = cycle.iterator();
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