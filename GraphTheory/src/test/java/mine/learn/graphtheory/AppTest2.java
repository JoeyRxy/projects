package mine.learn.graphtheory;

import java.io.IOException;
import org.junit.Test;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

/**
 * AppTest2
 */
public class AppTest2 {

    @Test
    public void testDirectedCycles() throws NumberFormatException, IOException {
        EdgeWeightedDiGraph g = (EdgeWeightedDiGraph) Helpers.getGraph("mediumEWD.txt", EdgeWeightedDiGraph.class);
        DirectedCycles cycles = new DirectedCycles(g);
        System.out.println(cycles);
    }

}