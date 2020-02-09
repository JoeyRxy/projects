package mine.learn.graphtheory;

import mine.learn.graphtheory.bean.Coordination;
import mine.learn.graphtheory.gui.UAVGraph;
import mine.learn.graphtheory.util.IndexedPriorityQueue;

/**
 * Astar
 */
@Deprecated
public class Astar {

    public Astar(@org.jetbrains.annotations.NotNull UAVGraph g, Coordination start, Coordination goal) {

        int vn = g.size();
        Coordination[] edgeTo = new Coordination[vn];
        double[] costTo = new double[vn];
        int startIdx = g.indexOf(start);
        edgeTo[startIdx] = start;
        for (int i = 0; i < costTo.length; i++) {
            costTo[i] = Double.POSITIVE_INFINITY;
        }
        costTo[startIdx] = 0;
        IndexedPriorityQueue<Double> openList = new IndexedPriorityQueue<>(vn);
        openList.add(startIdx, 0.);
        while (!openList.isEmpty()) {
            int index = openList.poll();
            Coordination from = g.coordinationOf(index);
            for (Coordination coordination : g.adj(from)) {
                int indexOf = g.indexOf(coordination);
                double d = costTo[index] + weight(from, coordination);// yess
                if (!openList.contains(indexOf) || costTo[indexOf] > d) {// 比较weight，但在
                    costTo[indexOf] = d;
                    edgeTo[indexOf] = from;
                    openList.add(indexOf, d + coordination.dist(goal));
                }
            }
        }
    }

    private double weight(@org.jetbrains.annotations.NotNull Coordination from, Coordination to) {
        int abs = Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY())
                + Math.abs(to.getZ() - from.getZ());
        if (abs == 1)
            return 1;
        else if (abs == 2)
            return 1.414;
        else if (abs == 3)
            return 1.732;
        else
            throw new IllegalArgumentException("参数错误");
    }

}