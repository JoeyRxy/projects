package mine.learn.graphtheory.bean;

public class WeightedDirectedEdge implements Comparable<WeightedDirectedEdge> {
    private int v;
    private int w;
    private double weight;

    public WeightedDirectedEdge(EdgeWeightedDiGraph g, String tail, String head, double weight) {
        this.v = g.indexOf(tail);
        this.w = g.indexOf(head);
        this.weight = weight;
    }

    public WeightedDirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedDirectedEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%-5d -> %5d : %-10.5f", v, w, weight);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + v;
        result = prime * result + w;
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeightedDirectedEdge other = (WeightedDirectedEdge) obj;
        if (v != other.v)
            return false;
        if (w != other.w)
            return false;
        if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
            return false;
        return true;
    }

}