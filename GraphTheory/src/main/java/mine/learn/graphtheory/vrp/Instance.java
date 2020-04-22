package mine.learn.graphtheory.vrp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Instance {
    private int[] order;
    private LinkedList<SubPath> subPaths;
    private int[] path;
    private double dist;
    private LinkedList<int[]> decodePath;

    public Instance(int[] order) {
        this.order = order;
        genSubPath();
        assert checkVehicleLoad() : "超载了！";
        dist = 0;
        path = new int[order.length + subPaths.size() - 1];
        int idx = 0;
        decodePath = new LinkedList<>();
        for (SubPath subPath : subPaths) {
            DistAssociatedWithPath _distaAssociatedWithPath = VRPSA.memo.get(subPath);
            if (_distaAssociatedWithPath != null) {
                dist += _distaAssociatedWithPath.getDist();
                int[] _path = _distaAssociatedWithPath.getPath();
                decodePath.add(_path);
                copy(_path, path, 0, _path.length, idx);
                idx += _path.length;
            } else {
                DistAssociatedWithPath minDistAndPath = subPath.getMinDistAndPath();
                VRPSA.memo.put(subPath, minDistAndPath);
                dist += minDistAndPath.getDist();
                int[] _path = minDistAndPath.getPath();
                decodePath.add(_path);
                copy(_path, path, 0, _path.length, idx);
                idx += _path.length;
            }
        }
    }

    public Instance() {
    }

    private void genSubPath() {
        subPaths = new LinkedList<>();
        double s;
        int _t, n;
        int i = 2;
        while (i != order.length) {
            s = 0;
            _t = --i;
            n = 0;
            while (s <= VRPSA.vihicleCapacity && n < 20) {
                s += VRPSA.demands[order[i]];
                i++;
                if (i == order.length)
                    break;
                n++;
            }
            // int[] copy = Arrays.copyOfRange(order, _t, i);
            int[] copy = new int[i - _t];
            copy[0] = 0;
            for (int j = 1; j < copy.length; j++) {
                copy[j] = order[_t++];
            }
            Arrays.sort(copy);
            subPaths.add(new SubPath(copy));
        }
    }

    private boolean checkVehicleLoad() {
        double load;
        for (SubPath sub : subPaths) {
            load = 0;
            for (int v : sub.getPath()) {
                load += VRPSA.demands[v];
            }
            // System.out.println(load);
            if (load > VRPSA.vihicleCapacity)
                return false;
        }
        return true;
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

    /**
     * check whether dist == Sum(path)
     * 
     * @return
     */
    public boolean check() {
        double s = 0;
        for (int i = 1; i < path.length; i++) {
            s += VRPSA.g[path[i - 1]][path[i]];
        }
        s += VRPSA.g[path[path.length - 1]][path[0]];
        return Math.abs(s - dist) < 1e-7;
    }

    /**
     * @return the decodePath
     */
    public List<int[]> getDecodePath() {
        return decodePath;
    }
}