package mine.learn.graphtheory.gui;

import org.junit.Test;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;

/**
 * UAVGTest
 */
public class UAVGTest {

    @Test
    public void testUAVGraph() {
        UAVGraph g = new UAVGraph(3, 4, 5);
        g.setTrue(1, 2, 3);
        g.setTrue(1, 2, 4);
        g.setTrue(1, 3, 3);
        g.setTrue(2, 2, 3);
        g.setTrue(0, 2, 3);
        g.setTrue(0, 1, 3);
        g.setTrue(2, 0, 1);
        g.setTrue(1, 0, 1);
        g.setTrue(1, 1, 3);
        g.setTrue(1, 2, 2);
        g.setTrue(1, 1, 1);
        g.setTrue(1, 2, 0);
        g.setTrue(2, 3, 3);
        g.setTrue(2, 2, 4);

        EdgeWeightedGraph graph = g.toEWGraph();
        System.out.println(graph);
    }

}