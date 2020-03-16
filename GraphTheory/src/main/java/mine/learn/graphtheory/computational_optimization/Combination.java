package mine.learn.graphtheory.computational_optimization;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Combination
 */
public class Combination {

    private int[] set;
    private int k;

    private List<int[]> res;
    private int[] tmp;

    private boolean[] marked;

    public List<int[]> comb(int[] set, int k) {
        this.set = set;
        this.k = k;
        res = new LinkedList<>();
        tmp = new int[k];
        marked = new boolean[set.length];
        dfs(0, 0);
        return res;
    }

    private void dfs(int depth, int prev) {
        if (depth == k) {
            res.add(Arrays.copyOf(tmp, tmp.length));
            return;
        }
        for (int i = prev; i < set.length; i++) {
            if (!marked[i]) {
                marked[i] = true;
                tmp[depth] = set[i];
                dfs(depth + 1, i + 1);
                marked[i] = false;
            }
        }
    }

}