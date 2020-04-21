package mine.learn.graphtheory.vrp;

import java.util.Arrays;

public class DistAssociatedWithPath {

    private double dist;
    private int[] path;

    public DistAssociatedWithPath(double dist, int[] path) {
        this.dist = dist;
        this.path = path;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new DistAssociatedWithPath(dist, path.clone());
    }

    @Override
    public String toString() {
        return "[dist=" + dist + ", path=" + Arrays.toString(path) + "]";
    }

}