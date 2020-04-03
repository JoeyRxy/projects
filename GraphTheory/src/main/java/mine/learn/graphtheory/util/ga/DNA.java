package mine.learn.graphtheory.util.ga;

import java.util.Arrays;

/**
 * DNA
 */
public class DNA implements Comparable<DNA> {
    private Gene[] genes;
    private double fitness;
    private FitnessCalculator calculator;

    @Override
    public int compareTo(DNA o) {
        return Double.compare(fitness, o.fitness);
    }

    public Gene[] getGenes() {
        return genes;
    }

    public void changed() {
        calculator.calc(this);
    }

    public DNA(Gene[] genes, FitnessCalculator calculator) {
        this.calculator = calculator;
        this.genes = genes;
        fitness = calculator.calc(this);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}