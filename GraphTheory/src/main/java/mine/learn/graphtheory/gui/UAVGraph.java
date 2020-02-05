package mine.learn.graphtheory.gui;

import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedEdge;

/**
 * DataProcessor
 */
public class UAVGraph {

    private boolean[][][] map;

    public UAVGraph(boolean[][][] map) {
        this.map = map;
    }

    public UAVGraph(int a, int b, int h) {
        map = new boolean[a][][];
        for (int i = 0; i < a; i++) {
            map[i] = new boolean[b][];
            for (int j = 0; j < b; j++) {
                map[i][j] = new boolean[h];
            }
        }
    }

    public void setTrue(int i, int j, int k) {
        map[i][j][k] = true;
    }

    public EdgeWeightedGraph toEWDGraph() {
        int len3 = map[0][0].length;
        int len2 = map[0].length;
        int len = map.length;
        int V = len * len2 * len3;
        EdgeWeightedGraph g = new EdgeWeightedGraph(V);
        int idx = 0;
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len2; j++)
                for (int k = 0; k < len3; k++)
                    g.setIndexOf(i + "" + j + "" + k + "", idx++);

        for (int i = 0; i < len; i++)
            for (int j = 0; j < len2; j++)
                for (int k = 0; k < len3; k++)
                    addNodeEdge(g, i, j, k);

        return g;
    }

    private void addNodeEdge(EdgeWeightedGraph g, int i, int j, int k) {
        int idx = g.indexOf(i + "" + j + "" + k);
        for (int l = i - 1; l < i + 2; l++)
            for (int m = j - 1; m < j + 2; m++)
                for (int n = k - 1; n < k + 2; n++)
                    if (!map[i][j][k])
                        if (l != i && m != j && m != n)
                            if (is(l, m, n))
                                g.addEdge(new WeightedEdge(g.indexOf(l + "" + m + "" + m), idx,
                                        weight(i, j, k, l, m, n)));

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
}