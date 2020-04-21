package mine.learn.graphtheory.computational_optimization.heuristic;

import java.util.Arrays;
import java.util.Random;

/**
 * Gene
 */
public class Gene implements Comparable<Gene> {
    Random r;
    int[] order;
    double dist;
    double[][] g;

    public void shuffle(int times) {
        for (int i = 0; i < times; i++) {
            swap(1 + r.nextInt(order.length - 1), 1 + r.nextInt(order.length - 1));
        }
        calcDist();
    }

    private void swap(int i, int j) {
        int t = order[i];
        order[i] = order[j];
        order[j] = t;
    }

    private void calcDist() {
        double s = 0;
        int i;
        for (i = 1; i < order.length; i++) {
            s += g[order[i - 1]][order[i]];
        }
        s += g[order[i - 1]][order[0]];
        dist = s;
    }

    public void mutate() {
        swap(1 + r.nextInt(order.length - 1), 1 + r.nextInt(order.length - 1));
        calcDist();
    }

    public Gene(Random r, int[] order, double[][] g) {
        this.r = r;
        this.order = order.clone();
        this.g = g;
    }

    @Override
    /**
     * fitness越小越好
     */
    public int compareTo(Gene o) {
        return Double.compare(dist, o.dist);
    }

    /**
     * 这方法太菜了，跟瞎搞一通有什么区别？
     * 
     * @param select
     */
    public void crossover(Gene select) {
        int v = order.length / 2 + r.nextInt(order.length);
        for (int i = v; i < order.length; i++) {
            for (int j = 0; j < order.length; j++) {
                if (!in(select.order[j], i)) {// 在0-i之间没有select.order[j]
                    order[i] = select.order[j];
                    break;
                }
            }
        }
        calcDist();
    }

    private boolean in(int x, int i) {
        for (int j = 0; j < i; j++) {
            if (order[j] == x)
                return true;
        }
        return false;
    }

}