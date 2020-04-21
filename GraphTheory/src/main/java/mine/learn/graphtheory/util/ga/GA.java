package mine.learn.graphtheory.util.ga;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.util.Helpers;
import mine.learn.graphtheory.util.SortedList;

/**
 * GA
 */
public class GA {

    SortedList<DNA> population;
    private Random r;
    private FitnessCalculator calculator;
    private int sigma;
    private int len;

    public GA(int populationSize, Gene[] initGenes, int times, int sigma, double pc, double pm,
            FitnessCalculator calculator) {
        this.calculator = calculator;
        this.sigma = sigma;
        this.len = initGenes.length;
        r = new Random(System.currentTimeMillis());
        population = init(populationSize, initGenes);
        int crossoverTimes = (int) (pc * populationSize);
        int mutationTimes = (int) (pm * populationSize);
        while (times-- > 0) {
            // 更新族群
            for (int i = 0; i < crossoverTimes; i++) {
                int _1 = select();
                int _2 = select();
                DNA dna1 = population.get(_1);
                DNA dna2 = population.get(_2);
                crossover(dna1, dna2);
                population.change(_1, dna1);
                population.change(_2, dna2);
            }
            for (int i = 0; i < mutationTimes; i++) {
                int idx = select();
                DNA dna = population.get(idx);
                mutate(dna);
                population.change(idx, dna);
            }
        }

    }

    private void mutate(DNA dna) {
        int swaptimes = r.nextInt(len >> 1);// adjustable
        Gene[] genes = dna.getGenes();
        while (swaptimes-- > 0) {
            int i = r.nextInt(len);
            int j = r.nextInt(len);
            while (j == i)
                j = r.nextInt(len);
            swap(genes, i, j);
        }
        dna.changed();
    }

    private void crossover(DNA dna1, DNA dna2) {
        int idx = r.nextInt(len);// adjustable
        Gene[] genes1 = dna1.getGenes();
        Gene[] genes2 = dna2.getGenes();
        Gene tmp;
        while (idx != len) {
            tmp = genes1[idx];
            genes1[idx] = genes2[idx];
            genes2[idx] = tmp;
            idx++;
        }
        dna1.changed();
        dna2.changed();
    }

    private int select() {
        return (int) Math.abs(r.nextGaussian() * sigma);// adjustable
    }

    private SortedList<DNA> init(int populationSize, Gene[] initGenes) {
        DNA[] dnas = new DNA[populationSize];
        dnas[0] = new DNA(initGenes, calculator);
        for (int i = 1; i != populationSize; i++) {
            dnas[i] = new DNA(shuffle(initGenes), calculator);
        }
        return new SortedList<>(dnas);
    }

    private Gene[] shuffle(Gene[] initGenes) {
        Gene[] genes = initGenes.clone();
        for (int l = 0; l != len; l++) {
            int i = r.nextInt(len);
            int j = r.nextInt(len);
            while (j == i)
                j = r.nextInt(len);
            swap(genes, i, j);
        }
        return genes;
    }

    private void swap(Gene[] genes, int i, int j) {
        Gene tmp = genes[i];
        genes[i] = genes[j];
        genes[j] = tmp;
    }

    public DNA getBestDNA() {
        return population.get(0);
    }

    public static void main(String[] args) throws IOException {
        EdgeWeightedDiGraph g = Helpers.parseJSON(new File("src/main/resources/ch130.json"), null);
        FloydWarshall spt = new FloydWarshall(g);
        double[][] graph = new double[g.V()][g.V()];
        g = null;
        for (int u = 0; u < graph.length; u++) {
            for (int v = 0; v < graph.length; v++) {
                graph[u][v] = spt.dist(u, v);
            }
        }
        spt = null;
        TSPGene[] genes = new TSPGene[graph.length];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new TSPGene(i);
        }
        GA ga = new GA(1000, genes, 1000, 10, 0, 1, new FitnessCalculator() {

            @Override
            public double calc(DNA dna) {
                return calcDist(dna);
            }

            private double calcDist(DNA dna) {
                double s = 0;
                TSPGene[] genes = (TSPGene[]) dna.getGenes();
                for (int i = 0; i < genes.length - 1; i++) {
                    s += graph[genes[i].x][genes[i + 1].x];
                }
                s += graph[genes[genes.length - 1].x][genes[0].x];
                return s;
            }
        });
        DNA bestDNA = ga.getBestDNA();
        System.out.println(bestDNA);
        TSPGene[] bestOrder = (TSPGene[]) bestDNA.getGenes();
        double s = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            s += graph[bestOrder[i].x][bestOrder[i + 1].x];
        }
        s += graph[bestOrder[bestOrder.length - 1].x][bestOrder[0].x];
        System.out.println(s);
    }
}