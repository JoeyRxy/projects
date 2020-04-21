package mine.learn.graphtheory.computational_optimization.heuristic;

import java.util.Arrays;
import java.util.Random;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.util.PriorityQueueExt;

/**
 * TSP4
 * <p>
 * GA
 */
@Deprecated
public class TSP4 {
    private double[][] g;
    private int V;
    private int population;
    private PriorityQueueExt<Gene> genes;
    private Random r;
    /** 基因的适应度 */
    private double mutateRate;
    private double crossRate;
    private double sigma;
    private int[] orderRecord;
    private double distRecord;

    /**
     * 
     * @param population
     * @param graph
     * @param set        第一个<code>set[0]</code>为源节点
     */
    public TSP4(EdgeWeightedDiGraph graph, int[] set, int population, int gen, double mutateRate, double crossRate,
            double sigma) {
        V = set.length;
        g = new double[V][V];
        FloydWarshall floydwarshall = new FloydWarshall(graph);
        for (int i = 0; i < set.length; i++) {
            for (int j = 0; j < set.length; j++) {
                g[i][j] = floydwarshall.dist(set[i], set[j]);
            }
        }
        this.sigma = sigma;
        r = new Random(System.currentTimeMillis());
        this.population = population;
        this.mutateRate = mutateRate;
        this.crossRate = crossRate;
        genes = new PriorityQueueExt<>(population + 1);
        int[] order = new int[V];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        // init genes lib
        Gene _t = new Gene(r, order, g);
        _t.shuffle(0);
        genes.add(_t);
        System.out.println("dist :: " + _t.dist);
        for (int i = 1; i < population; i++) {
            Gene gene = new Gene(r, order, g);
            gene.shuffle(r.nextInt(population));
            genes.add(gene);
        }
        distRecord = genes.peek().dist;
        orderRecord = genes.peek().order;
        System.out.println("开始：" + distRecord);
        while (gen > 0) {
            nextGen();
            gen--;
            Gene peek = genes.peek();
            if (peek.dist < distRecord) {
                distRecord = peek.dist;
                orderRecord = peek.order.clone();
            }
        }
    }

    private void nextGen() {
        for (int i = 0; i < population; i++) {
            int idx = select();
            Gene tmp = genes.get(idx);
            if (Math.random() < crossRate)
                tmp.crossover(genes.get(select()));
            if (Math.random() < mutateRate)
                tmp.mutate();
            genes.change(idx, tmp);
        }
    }

    private int select() {
        return 1 + ((int) Math.abs(r.nextGaussian() * sigma)) % population;
    }

    public double res() {
        return distRecord;
    }

    public int[] path() {
        return orderRecord;
    }

}