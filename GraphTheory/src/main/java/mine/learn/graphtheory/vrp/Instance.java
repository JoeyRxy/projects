package mine.learn.graphtheory.vrp;

import java.util.Arrays;
import java.util.LinkedList;

public class Instance {
    private int[] order;
    private LinkedList<SubPath> subPaths;
    private int[] path;
    private double dist;

    public Instance(int[] order) {
        this.order = order;
        genSubPath();
        dist = 0;
        path = new int[order.length + subPaths.size() - 1];
        int idx = 0;
        for (SubPath subPath : subPaths) {
            DistAssociatedWithPath _distaAssociatedWithPath = VRPSA.memo.get(subPath);
            if (_distaAssociatedWithPath != null) {
                dist += _distaAssociatedWithPath.getDist();
                int[] _path = _distaAssociatedWithPath.getPath();
                copy(_path, path, 0, _path.length, idx);
                idx += _path.length;
            } else {
                DistAssociatedWithPath minDistAndPath = subPath.getMinDistAndPath();
                VRPSA.memo.put(subPath, minDistAndPath);
                dist += minDistAndPath.getDist();
                int[] _path = minDistAndPath.getPath();
                copy(_path, path, 0, _path.length, idx);
                idx += _path.length;
            }
        }
    }

    private void genSubPath() {
        subPaths = new LinkedList<>();
        double s;
        int _t;
        for (int i = 0; i < order.length;) {
            s = 0;
            _t = i;
            while (s <= VRPSA.vihicleCapacity && i < order.length) {
                s += VRPSA.demands[i];
                i++;
            }
            int[] copy = Arrays.copyOfRange(order, _t, i);
            Arrays.sort(copy);
            subPaths.add(new SubPath(copy));
        }
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int[] order) {
        this.order = order;
        genSubPath();
    }

    /**
     * cloned
     * 
     * @return the order
     */
    public int[] getOrder() {
        return order.clone();
    }

    /**
     * @return the dist
     */
    public double getDist() {
        return dist;
    }

    /**
     * @return the path
     */
    public int[] getPath() {
        return path;
    }

    /**
     * 左闭右开的区间复制
     * 
     * @param original
     * @param target
     * @param originalFrom
     * @param originalTo
     * @param targetFrom
     */
    private void copy(int[] original, int[] target, int originalFrom, int originalTo, int targetFrom) {
        int idx = targetFrom;
        for (int i = originalFrom; i < originalTo; i++) {
            target[idx++] = original[i];
        }
    }

    @Override
    public String toString() {
        return "Instance [dist=" + dist + ", path=" + Arrays.toString(path) + "]";
    }
}