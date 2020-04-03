package mine.learn.graphtheory.util.ga;

/**
 * MutationHandler
 */
public interface MutationHandler {

    public void mutate(DNA dna, Object... params);
}