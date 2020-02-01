
package mine.learn.graphtheory.bean;

public class WeightedEdge implements Comparable<WeightedEdge> {

    private int v;
    private int w;
    private double weight;

    public WeightedEdge(int v, int w, double weight) {
        if (v < 0)
            throw new IllegalArgumentException("节点值必须为正整数");
        if (w < 0)
            throw new IllegalArgumentException("节点值必须为正整数");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Illegal endpoint");
    }

    @Override
    public int compareTo(WeightedEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() {
        return String.format("%-5d -%-5d : %-10.5f", v, w, weight);
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
        WeightedEdge other = (WeightedEdge) obj;
        if (v != other.v)
            return false;
        if (w != other.w)
            return false;
        if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
            return false;
        return true;
    }

}
