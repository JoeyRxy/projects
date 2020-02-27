
package mine.learn.graphtheory;

import java.util.LinkedList;
import java.util.Queue;

import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

public class TopologicalX {
    private Queue<Integer> order;
    private int[] ranks;

    public TopologicalX(EdgeWeightedDiGraph G) {

        int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            indegree[v] = G.indegree(v);
        }

        ranks = new int[G.V()];
        order = new LinkedList<Integer>();
        int count = 0;

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int v = 0; v < G.V(); v++)
            if (indegree[v] == 0)
                queue.add(v);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);
            ranks[v] = count++;
            for (WeightedDirectedEdge e : G.adjOf(v)) {
                int w = e.to();
                indegree[w]--;
                if (indegree[w] == 0)
                    queue.add(w);
            }
        }

        if (count != G.V()) {
            order = null;
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public int rank(int v) {
        validateVertex(v);
        if (hasOrder())
            return ranks[v];
        else
            return -1;
    }

    private void validateVertex(int v) {
        int V = ranks.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}