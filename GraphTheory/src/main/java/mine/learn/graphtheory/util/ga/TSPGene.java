package mine.learn.graphtheory.util.ga;

public class TSPGene implements Gene {

    int x;

    public TSPGene(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return x + "";
    }
}
