package mine.learn.graphtheory.gui;

import java.util.ArrayList;
import java.util.List;

import mine.learn.graphtheory.bean.Coordination;
import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;

/**
 * UAVGraph
 */
public class UAVGraph {

    private boolean[][][] map;
    private int[][][] indexOf;

    public UAVGraph(boolean[][][] map) {
        this.map = map;
    }

    public UAVGraph(int a, int b, int h) {
        map = new boolean[a][][];
        indexOf = new int[a][][];
        int idx = 0;
        for (int i = 0; i < a; i++) {
            indexOf[i] = new int[b][];
            map[i] = new boolean[b][];
            for (int j = 0; j < b; j++) {
                map[i][j] = new boolean[h];
                indexOf[i][j] = new int[h];
                for (int k = 0; k < h; k++) {
                    indexOf[i][j][k] = idx++;
                }
            }
        }
    }

    public int size() {
        return map.length * map[0].length * map[0][0].length;
    }

    public int indexOf(int i, int j, int k) {
        return indexOf[i][j][k];
    }

    public int indexOf(Coordination coordination) {
        return indexOf(coordination.getX(), coordination.getY(), coordination.getZ());
    }

    public void setTrue(int i, int j, int k) {
        map[i][j][k] = true;
    }

    public EdgeWeightedGraph toEWGraph() {
        int len3 = map[0][0].length;
        int len2 = map[0].length;
        int len = map.length;
        int V = len * len2 * len3;
        EdgeWeightedGraph g = new EdgeWeightedGraph(V);

        for (int i = 0; i < len; i++)
            for (int j = 0; j < len2; j++)
                for (int k = 0; k < len3; k++)
                    addNodeEdge(g, i, j, k);

        return g;
    }

    private void addNodeEdge(EdgeWeightedGraph g, int i, int j, int k) {
        int idx = indexOf[i][j][k];
        for (int l = i - 1; l < i + 2; l++)
            for (int m = j - 1; m < j + 2; m++)
                for (int n = k - 1; n < k + 2; n++)
                    if (!map[i][j][k])
                        if (l != i && m != j && m != n)
                            if (is(l, m, n))
                                g.addEdge(new WeightedEdge(indexOf[l][m][n], idx, weight(i, j, k, l, m, n)));

    }

    private boolean is(int l, int m, int n) {
        return l >= 0 && l < map.length && m >= 0 && m < map[0].length && n >= 0 && n < map[0][0].length
                && map[l][m][n];
    }

    private double weight(int i, int j, int k, int l, int m, int n) {
        int abs = Math.abs(i - l) + Math.abs(j - m) + Math.abs(k - n);
        if (abs == 1)
            return 1;
        else if (abs == 2)
            return 1.414;
        else if (abs == 3)
            return 1.732;
        else
            throw new IllegalArgumentException("参数错误");
    }

    /**
     * TODO : NEED TEST
     * 
     * @param index
     * @return
     */
    public Coordination coordinationOf(int index) {
        int len3 = map[0][0].length;
        int a1 = map[0].length * len3;
        int x = index / a1;
        int nothing = index - x * a1;
        int y = nothing / len3;
        int z = nothing - y * len3;
        return new Coordination(x, y, z);
    }

    public List<Coordination> adj(int index) {
        return adj(coordinationOf(index));
    }

    public List<Coordination> adj(Coordination coordination) {
        List<Coordination> list = new ArrayList<>(8);
        int x = coordination.getX();
        int y = coordination.getY();
        int z = coordination.getZ();
        for (int i = x - 1; i < x + 2; i++)
            for (int j = y - 1; j < y + 2; j++)
                for (int k = z - 1; k < z + 2; k++)
                    if (is(i, j, k))
                        list.add(new Coordination(i, j, k));

        return list;
    }
}