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

    /**
     * @deprecated 重复记录两个图，只为一个reverse()方法太占内存，不如以时间换空间
     */
    private EdgeWeightedDiGraph reverseDiGraph;

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

    /**
     * 使用无参构造的图需在调用addEdge方法前调用addVertex
     */
    public EdgeWeightedDiGraph() {
        V = 0;
        vertexSet = new HashSet<>();
        adjMap = new HashMap<>();
        indegree = new HashMap<>();
    }

    /**
     * 使用无参构造的图需在调用addEdge方法前调用addVertex
     * 
     * @param vertex
     */
    public void addVertex(String vertex) {
        if (vertexSet.contains(vertex))
            return;

        V++;
        vertexSet.add(vertex);
        adjMap.put(vertex, new HashSet<>());
        indegree.put(vertex, 0);
    }

    /**
     * 使用无参构造的图需在调用该方法前调用addVertex
     * 
     * @param e
     */
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

    /**
     * O(VE)
     */
    public EdgeWeightedDiGraph reverse() {
        EdgeWeightedDiGraph reverseGraph = new EdgeWeightedDiGraph();
        for (String v : vertexSet) {
            Set<DirectedEdge> edgeSet = adjMap.get(v);
            for (DirectedEdge e : edgeSet) {
                String from = e.from();
                reverseGraph.addVertex(from);
                String to = e.to();
                reverseGraph.addVertex(to);
                reverseGraph.addEdge(new DirectedEdge(to, from, e.weight()));
            }
        }
        return reverseGraph;
    }
}