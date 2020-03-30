package mine.learn.graphtheory.bean;

import java.util.Objects;

public class WeightedDirectedEdge implements Edge, Comparable<WeightedDirectedEdge> {
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
        return String.format("%5d -> %-5d : %-10.5f", v, w, weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w);
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
        return v == other.v && w == other.w;
    }

}