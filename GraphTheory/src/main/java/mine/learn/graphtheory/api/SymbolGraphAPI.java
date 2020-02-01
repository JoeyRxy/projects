package mine.learn.graphtheory.api;

/**
 * SymbolAPI
 */
public interface SymbolGraphAPI {

    public int indexOf(String vertex);

    public String nameOf(int v);

    public void setIndexOf(String vertex, int index);
}