package mine.learn.graphtheory.vrp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * <code>source</code> -> v1 -> v2 ...
 */
public class SubPath {
    private int[] path;
    private HashMap<HashSet<Integer>, Double>[] memos;
    private HashMap<HashSet<Integer>, Integer>[] pathFrom;

    private int hashCode;
    private double minDist;
    private int[] minPath;

    private boolean isSorted() {
        if (path[0] != 0)
            return false;
        for (int i = 1; i < memos.length; i++) {
            if (path[i] <= path[i - 1])
                return false;
        }
        return true;
    }

    public SubPath(int[] path) {
        this.path = path;
        assert isSorted() : "没有排序的路径！";
        final int prime = 31;
        hashCode = 1;
        hashCode = prime * hashCode + Arrays.hashCode(path);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SubPath subPath = new SubPath(path.clone());
        subPath.hashCode = hashCode;
        subPath.minPath = minPath.clone();
        subPath.minDist = minDist;
        return subPath;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubPath other = (SubPath) obj;
        return Arrays.equals(path, other.path);
    }

    /**
     * @return the path
     */
    public int[] getPath() {
        return path.clone();
    }

    /**
     * @param path the path to set
     */
    public void setPath(int[] path) {
        this.path = path;
        assert isSorted() : "没有排序的路径！";
        final int prime = 31;
        hashCode = 1;
        hashCode = prime * hashCode + Arrays.hashCode(path);
    }

    @SuppressWarnings("unchecked")
    private void tspdp() {
        memos = new HashMap[path.length];
        pathFrom = new HashMap[path.length];
        for (int i = 0; i < memos.length; i++) {
            memos[i] = new HashMap<>();
            memos[i].put(null, VRPSA.g[path[i]][path[0]]);
            pathFrom[i] = new HashMap<>();
            pathFrom[i].put(null, path[0]);
        }
        HashSet<Integer> init = new HashSet<>(path.length);
        for (int i = 1; i < path.length; i++) {
            init.add(path[i]);
        }
        minDist = memoDFS(path[0], init);
        minPath = new int[path.length];
        minPath[0] = path[0];
        for (int i = 1; i < path.length; i++) {
            minPath[i] = pathFrom[minPath[i - 1]].get(init);
            init.remove(minPath[i]);
        }
    }

    // @SuppressWarnings("unchecked")
    private double memoDFS(int u, HashSet<Integer> S) {
        Double _t = memos[u].get(S.size() == 0 ? null : S);
        if (_t != null)
            return _t.doubleValue();
        double ret;
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (Integer v : S) {
            if (Double.isFinite(VRPSA.g[u][v]))
                continue;
            S.remove(v);
            ret = VRPSA.g[u][v] + memoDFS(v, S);
            S.add(v);
            if (min > ret) {
                min = ret;
                w = v;
            }
        }
        // 不需要Clone
        // memos[u].put((HashSet<Integer>) S.clone(), min);
        // pathFrom[u].put((HashSet<Integer>) S.clone(), w);
        memos[u].put(S, min);
        pathFrom[u].put(S, w);
        return min;
    }

    public DistAssociatedWithPath getMinDistAndPath() {
        tspdp();
        return new DistAssociatedWithPath(minDist, minPath);
    }

}