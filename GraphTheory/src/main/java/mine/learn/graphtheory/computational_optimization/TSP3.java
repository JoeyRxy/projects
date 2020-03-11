package mine.learn.graphtheory.computational_optimization;

import java.util.Set;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * TSP3
 */
public class TSP3 {

    private EdgeWeightedDiGraph g;
    private int source;
    private int V;
    private double[][] memo;
    private int[][] pathFrom;

    public TSP3(EdgeWeightedDiGraph g, int source, Set<Integer> set) {
        this.g = g;
        this.source = source;
        V = g.V();
        memo = new double[V][1 << V];
        pathFrom = new int[V][1 << V];
        for (int i = 0; i < V; i++) {

        }
    }
}