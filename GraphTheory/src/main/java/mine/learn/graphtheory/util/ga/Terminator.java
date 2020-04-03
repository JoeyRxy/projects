package mine.learn.graphtheory.util.ga;

/**
 * Terminator
 */
public interface Terminator {

    public boolean shouldTerminate(Object... params);
}