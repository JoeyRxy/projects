package mine.learn.graphtheory.computational_optimization.heuristic;

import java.util.Arrays;

public class DNA implements Comparable<DNA> {

    private int[] genes;
    private double fitness;
    private double[][] g;

    @Override
    public int compareTo(DNA o) {
        return Double.compare(fitness, o.fitness);
    }

    public int[] getGenes() {
        return genes;
    }

    public void changed() {
        calc();
    }

    public DNA(int[] genes, double[][] g) {
        this.genes = genes;
        this.g = g;
        calc();
    }

    public DNA(DNA dna) {
        this.genes = dna.genes.clone();
        this.g = dna.g;
        fitness = dna.fitness;
    }

    private void calc() {
        fitness = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            fitness += g[genes[i]][genes[i + 1]];
        }
        fitness += g[genes[genes.length - 1]][genes[0]];
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }

}
