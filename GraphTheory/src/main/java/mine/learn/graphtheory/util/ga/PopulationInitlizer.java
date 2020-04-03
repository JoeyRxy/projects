package mine.learn.graphtheory.util.ga;

import java.util.Random;

import mine.learn.graphtheory.util.SortedList;

/**
 * PopulationInitlizer
 */
public interface PopulationInitlizer {

    SortedList<DNA> init(int populationSize, Random r, FitnessCalculator calculator);
}