package mine.learn.graphtheory.vrp;

import java.util.Arrays;

/**
 * <code>source</code> -> v1 -> v2 ...
 */
public class SubPath {
    private int[] path;
    // private HashMap<HashSet<Integer>, Double>[] memos;
    // private HashMap<HashSet<Integer>, Integer>[] pathFrom;
    private double[][] memo;
    private int[][] pathFrom;

    private int hashCode;
    private double minDist;
    private int[] minPath;
    private int V;

    private boolean isSorted() {
        for (int i = 1; i < V; i++) {
            if (path[i] <= path[i - 1])
                return false;
        }
        return true;
    }

    public SubPath(int[] path) {
        this.path = path;
        assert path[0] == 0 : "源节点必须为0";
        assert isSorted() : "没有排序的路径！";
        this.V = path.length;
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
        assert path[0] == 0 : "源节点必须为0";
        assert isSorted() : "没有排序的路径！";
        final int prime = 31;
        hashCode = 1;
        hashCode = prime * hashCode + Arrays.hashCode(path);
    }

    // /** 这个方法太慢了 */
    // @SuppressWarnings("unchecked")
    // private void tspdp() {
    // memos = new HashMap[V];
    // pathFrom = new HashMap[V];
    // for (int i = 0; i < memos.length; i++) {
    // memos[i] = new HashMap<>();
    // memos[i].put(null, VRPSA.g[path[i]][path[0]]);
    // pathFrom[i] = new HashMap<>();
    // pathFrom[i].put(null, path[0]);
    // }
    // HashSet<Integer> S0 = new HashSet<>(V - 1);
    // for (int i = 1; i < V; i++) {// 不包括源节点0
    // S0.add(path[i]);
    // }
    // minDist = memoDFS(path[0], S0);
    // minPath = new int[V];
    // minPath[0] = path[0];
    // for (int i = 1; i < V; i++) {
    // minPath[i] = pathFrom[minPath[i - 1]].get(S0);
    // S0.remove(minPath[i]);
    // }
    // }

    // // @SuppressWarnings("unchecked")
    // private double memoDFS(int u, HashSet<Integer> S) {
    // Double _t = memos[u].get(S.size() == 0 ? null : S);
    // if (_t != null)
    // return _t.doubleValue();
    // double ret;
    // double min = Double.POSITIVE_INFINITY;
    // int w = -1;
    // for (Integer v : S) {
    // if (Double.isInfinite(VRPSA.g[u][v]))
    // continue;
    // HashSet<Integer> tmp = (HashSet<Integer>) S.clone();
    // tmp.remove(v);
    // ret = VRPSA.g[u][v] + memoDFS(v, tmp);
    // // S.add(v);
    // if (min > ret) {
    // min = ret;
    // w = v;
    // }
    // }
    // // 不需要Clone
    // // memos[u].put((HashSet<Integer>) S.clone(), min);
    // // pathFrom[u].put((HashSet<Integer>) S.clone(), w);
    // memos[u].put(S, min);
    // pathFrom[u].put(S, w);
    // return min;
    // }

    private void tspdp() {
        memo = new double[V][1 << V];
        pathFrom = new int[V][1 << V];
        int S0 = (1 << V) - 2;
        // init
        for (int v = 0; v < V; v++) {
            memo[v][0] = VRPSA.g[path[v]][0];
            pathFrom[v][0] = 0;
        }
        minDist = memoDFS(0, S0);
        minPath = new int[V];
        for (int i = 1; i < V; i++) {
            minPath[i] = pathFrom[minPath[i - 1]][S0];
            S0 &= (~(1 << minPath[i]));
        }
        for (int i = 1; i < V; i++) {
            minPath[i] = path[minPath[i]];
        }
    }

    private double memoDFS(int u, int S) {
        if (memo[u][S] > 0) {
            return memo[u][S];
        }
        double ret;
        int t = ~1, x = 1;
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (int i = 0; i < V; i++, x <<= 1, t = (t << 1) | (t >>> -1)) {
            if (Double.isInfinite(VRPSA.g[path[u]][path[i]]))
                continue;
            if ((S & x) > 0) {// 遍历S中的节点
                ret = VRPSA.g[path[u]][path[i]] + memoDFS(i, S & t);
                if (min > ret) {
                    min = ret;
                    w = i;
                }
            }
        }
        memo[u][S] = min;
        pathFrom[u][S] = w;
        return min;
    }

    public DistAssociatedWithPath getMinDistAndPath() {
        tspdp();
        memo = null;
        pathFrom = null;
        return new DistAssociatedWithPath(minDist, minPath);
    }

    @Override
    public String toString() {
        return "SubPath [path=" + Arrays.toString(path) + "]";
    }

}