package mine.project.util;

/**
 * UnionFind;
 * <p>
 * find:O(log n)
 * <p>
 * union:approximately O(1)
 */
public class UnionFind {

    /** id : [0...n-1]->[0...n-1] */
    private int[] id;
    /** count : [0...n-1]->[0...n-1] */
    private int[] count;
    /** nums of components */
    private int size;

    public UnionFind(int n) {
        size = n;
        id = new int[n];
        count = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            count[i] = 1;
        }
    }

    /**
     * approximately O(1) :
     * 由于经过count判断之后会重新划分队伍，被并入的集合之后运行find()方法进行union时，并不完全是O(1)的时间。
     * <p>
     * do NOTHING while v and w are in same set
     * 
     * @param v
     * @param w
     */
    public void union(int v, int w) {
        v = find(v);
        w = find(w);
        if (v == w)
            return;
        int v_count = count[v];
        int w_count = count[w];
        if (v_count > w_count) {// w队大并入v队
            id[w] = v;
            count[v] += w_count;
        } else {// v队大并入w队
            id[v] = w;
            count[w] += v_count;
        }
        size--;
    }

    public int size() {
        return size;
    }

    /**
     * O(log n)
     * 
     * @param v
     * @return
     */
    public int find(int v) {
        int root = v;
        while (id[root] != root)
            root = id[root];
        int tmp = v;
        /**
         * while finding root, we set all member's root = {@code root}. to speed up
         * union method.
         */
        while (id[v] != v) {
            v = id[v];
            id[tmp] = root;
        }
        return root;
    }
}