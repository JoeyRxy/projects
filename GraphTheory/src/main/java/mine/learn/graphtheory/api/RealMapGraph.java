package mine.learn.graphtheory.api;

import mine.learn.graphtheory.bean.Coordination;

/**
 * RealMapGraph
 */
public interface RealMapGraph {

    public void setCoordinationOf(int v, Coordination coordination);

    public Coordination coordinationOf(int v);

    public int vertexOf(Coordination coordination);

    public double dist(int v, int w);
}