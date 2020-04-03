package mine.learn.graphtheory.util.ga;

/**
 * CrossoverHandler
 */
public interface CrossoverHandler {

    public void crossover(DNA dna1, DNA dna2, FitnessCalculator calculator, Object... params);
}