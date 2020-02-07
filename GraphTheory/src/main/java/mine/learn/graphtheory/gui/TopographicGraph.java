package mine.learn.graphtheory.gui;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;

/**
 * Topographic
 */
public class TopographicGraph {

    private byte[][] map;

    public TopographicGraph(int a, int b) {
        map = new byte[a][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new byte[b];
        }
    }

    public void set(int i, int j, byte f) {
        map[i][j] = f;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte[] bs : map) {
            for (byte b : bs) {
                builder.append(b).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public EdgeWeightedDiGraph toEWDG() {
        int len2 = map[0].length;
        int len = map.length;
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(len * len2);
        int idx = 0;
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len2; j++)
                g.setIndexOf(i + "" + j, idx++);

        for (int i = 0; i < len; i++)
            for (int j = 0; j < len2; j++)
                addNodeEdge(g, i, j);
        return g;
    }

    private void addNodeEdge(EdgeWeightedDiGraph g, int i, int j) {
        int idx = g.indexOf(i + "" + j);
        for (int m = i - 1; m < i + 2; m++) {
            for (int n = j - 1; n < j + 2; n++) {
                if (m != i && n != j)
                    if (is(m, n)) {
                        g.addEdge(new WeightedDirectedEdge(idx, g.indexOf(m + "" + n),
                                weight(i, j, m, n) + map[m][n] - map[i][j]));
                    }
            }
        }
    }

    private boolean is(int m, int n) {
        return m >= 0 && m < map.length && n >= 0 && n < map[0].length;
    }

    private double weight(int i, int j, int l, int m) {
        int abs = Math.abs(i - l) + Math.abs(j - m);
        if (abs == 1)
            return 1;
        else if (abs == 2)
            return 1.414;
        else
            throw new IllegalArgumentException("参数错误");
    }

}