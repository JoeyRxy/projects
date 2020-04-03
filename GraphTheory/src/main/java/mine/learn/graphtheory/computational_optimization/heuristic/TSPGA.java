package mine.learn.graphtheory.computational_optimization.heuristic;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.StrongComponents;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.Helpers;

/**
 * TSPGA
 */
public class TSPGA {

    private FloydWarshall spt;
    private int[] set;
    private int pSize;
    private double pc;
    private double pm;
    private DNA[] population;
    private Random r;
    private double[][] g;

    public TSPGA(EdgeWeightedDiGraph graph, int[] set, int pSize, int gen, double pc, double pm) {
        this.set = set;
        this.pSize = pSize;
        this.pc = pc;
        this.pm = pm;
        r = new Random(System.currentTimeMillis());
        StrongComponents scc = new StrongComponents(graph);
        if (!scc.stronglyConnected(set))
            throw new IllegalArgumentException();
        scc = null;
        spt = new FloydWarshall(graph);
        graph = null;
        int V = set.length;
        g = new double[V][V];
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                g[i][j] = spt.dist(i, j);
        //
        init();
        while (gen-- > 0)
            newGen();
    }

    private void init() {
        population = new DNA[pSize];
        int[] greedyOrder = getGreedyOrder();
        population[0] = new DNA(greedyOrder, g);
        for (int i = 1; i < pSize; i++)
            population[i] = new DNA(shuffle(greedyOrder), g);
        Arrays.sort(population);
    }

    private int[] shuffle(int[] initGenes) {
        int len = set.length;
        int[] genes = Arrays.copyOf(initGenes, len);
        for (int l = len >> 1; l != len; l++) {// n/2次？
            int i = r.nextInt(len);
            int j = r.nextInt(len);
            while (j == i)
                j = r.nextInt(len);
            swap(genes, i, j);
        }
        return genes;
    }

    private void swap(int[] genes, int i, int j) {
        int tmp = genes[i];
        genes[i] = genes[j];
        genes[j] = tmp;
    }

    private int[] getGreedyOrder() {
        double min = Double.POSITIVE_INFINITY;
        int[] best_order = null;
        int V = set.length;
        for (int i = 0; i < V; i++) {
            int[] order = new int[V];
            boolean[] vis = new boolean[V];
            double ret = greedy(i, order, vis, 0);
            // System.out.println("this ret : " + ret);
            if (min > ret) {
                min = ret;
                best_order = order;
            }
        }
        return best_order;
    }

    private double greedy(int v, int[] order, boolean[] vis, int depth) {
        if (depth == set.length - 1) {
            order[depth] = v;
            return g[v][order[0]];
        }
        vis[v] = true;
        order[depth] = v;
        double[] adj = g[v];
        double min = Double.POSITIVE_INFINITY;
        int w = -1;
        for (int i = 0; i < adj.length; i++) {
            if (!vis[i]) {
                if (adj[i] < min) {
                    min = adj[i];
                    w = i;
                }
            }
        }
        return min + greedy(w, order, vis, depth + 1);
    }

    private void rotateTo0(int[] best_order) {
        int tmp;
        while (best_order[0] != 0) {
            tmp = best_order[0];
            for (int i = 0; i < best_order.length - 1; i++) {
                best_order[i] = best_order[i + 1];
            }
            best_order[best_order.length - 1] = tmp;
        }
    }

    private float g(float x) {
        float y = x * x;
        y *= y;
        return y;
    }

    private int select() {
        return (int) (g(r.nextFloat()) * pSize);// adjustable
    }

    private void newGen() {
        DNA[] newPopulation = new DNA[pSize];
        newPopulation[0] = population[0];
        for (int i = 1; i < pSize; i++) {
            DNA t = population[select()];
            crossover_mutate(t);
            newPopulation[i] = t;
        }
        population = newPopulation;
        Arrays.sort(population);
    }

    private void crossover_mutate(DNA dna) {
        int[] genes = dna.getGenes();
        int V = set.length;
        int i = r.nextInt(V);
        int j = r.nextInt(V);
        while (j == i)
            j = r.nextInt(V);
        if (r.nextDouble() < pc) {// 块交换
            if (i > j) {
                int t = i;
                i = j;
                j = t;
            }
            for (int k = i; k < j; k++) {
                swap(genes, k, V - 1 - k);
            }
        }
        if (r.nextDouble() < pm) {
            swap(genes, i, j);
        }
        dna.changed();
    }

    public double getBestDist() {
        return population[0].getFitness();
    }

    public List<WeightedDirectedEdge> getBestPath() {
        int[] genes = population[0].getGenes();
        rotateTo0(genes);
        List<WeightedDirectedEdge> path = new LinkedList<>();
        for (int i = 0; i < genes.length - 1; i++) {
            path.addAll(spt.path(set[i], set[i + 1]));
        }
        path.addAll(spt.path(set[genes[genes.length - 1]], set[genes[0]]));
        return path;
    }

    public static void main(String[] args) throws IOException {
        EdgeWeightedDiGraph graph = Helpers.parseJSON(new File("src/main/resources/ch130.json"), null);
        int[] set = new int[graph.V()];
        for (int i = 0; i < set.length; i++) {
            set[i] = i;
        }
        TSPGA tspga = new TSPGA(graph, set, 1000, 1000, 0.8, 0.1);
        System.out.println(tspga.getBestDist());
        // System.out.println(tspga.getBestPath());
    }
}