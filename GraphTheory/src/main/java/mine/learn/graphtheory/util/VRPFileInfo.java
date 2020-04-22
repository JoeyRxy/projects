package mine.learn.graphtheory.util;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * VRPFileInfo
 */
public class VRPFileInfo {

    public EdgeWeightedDiGraph graph;
    public double bestDist;
    public int trucks;
    public double capacity;
    public double[] demands;

    public VRPFileInfo(EdgeWeightedDiGraph graph, double best, int trucks, double capacity, double[] demands) {
        this.graph = graph;
        this.bestDist = best;
        this.trucks = trucks;
        this.capacity = capacity;
        this.demands = demands;
    }

}