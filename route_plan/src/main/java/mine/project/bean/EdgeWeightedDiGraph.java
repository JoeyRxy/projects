package mine.project.bean;

import java.util.*;

/**
 * EdgeWeightedDiGraph
 * <p>
 * 节点名不建议含空格！会误认为是两个节点
 */
public class EdgeWeightedDiGraph {

    private int V;
    private int E;

    private Set<String> vertexSet;

    /**
     * {@code vertex} -> int : indegree of {@code vertex}
     */
    private Map<String, Integer> indegree;

    /**
     * 以节点为 tail 的{@link DirectedEdge}
     */
    private Map<String, Set<DirectedEdge>> adjMap;

    public EdgeWeightedDiGraph(int V) {
        this.V = V;
        vertexSet = new HashSet<>();
        adjMap = new HashMap<>();
        indegree = new HashMap<>();
        for (int i = 0; i < V; i++) {
            vertexSet.add(i + "");
            adjMap.put(i + "", new HashSet<>());
            indegree.put(i + "", 0);
        }
    }

    public EdgeWeightedDiGraph() {
        V = 0;
        vertexSet = new HashSet<>();
        adjMap = new HashMap<>();
        indegree = new HashMap<>();
    }

    public void addVertex(String vertex) {
        if (vertexSet.contains(vertex)) {
            System.err.println("节点 \"" + vertex + "\" 已存在");
            return;
        }
        V++;
        vertexSet.add(vertex);
        adjMap.put(vertex, new HashSet<>());
        indegree.put(vertex, 0);
    }

    public void addEdge(DirectedEdge e) {
        String from = e.from();
        String to = e.to();
        if (vertexSet.contains(from) && vertexSet.contains(to)) {
            adjMap.get(from).add(e);
            indegree.put(to, indegree.get(to) + 1);
            E++;
        }
    }

    public int indegreeOf(String vertex) {
        return indegree.get(vertex);
    }

    public int outdegreeOf(String vertex) {
        return adjMap.get(vertex).size();
    }

    public Set<DirectedEdge> adjOf(String vertex) {
        return adjMap.get(vertex);
    }

    public int V() {
        return V;
    }

    public Set<String> vertexSet() {
        return vertexSet;
    }

    public int E() {
        return E;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String vertex : vertexSet) {
            builder.append(vertex + " :\n");
            for (DirectedEdge edge : adjMap.get(vertex))
                builder.append("\t").append(edge).append("\n");
        }
        return builder.toString();
    }
}