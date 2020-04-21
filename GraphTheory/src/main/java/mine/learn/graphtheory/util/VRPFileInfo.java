package mine.learn.graphtheory.util;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * VRPFileInfo
 */
public class VRPFileInfo {

    public EdgeWeightedDiGraph graph;
    public double capacity;
    public double[] demands;

    public VRPFileInfo(EdgeWeightedDiGraph graph, double capacity, double[] demands) {
        this.graph = graph;
        this.capacity = capacity;
        this.demands = demands;
    }

}