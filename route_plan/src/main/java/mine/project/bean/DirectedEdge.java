package mine.project.bean;

/**
 * DirectedEdge
 */
public class DirectedEdge {

    /** tail to head */
    private String tail;
    /** tail to head */
    private String head;
    /** weight of the directed edge */
    private double weight;

    /**
     * 
     * @param tail   tail to head
     * @param head   tail to head
     * @param weight weight of the directed edge
     */
    public DirectedEdge(String tail, String head, double weight) {
        this.tail = tail;
        this.head = head;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public String from() {
        return tail;
    }

    public String to() {
        return head;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s (weight : %-7.2f) ", tail, head, weight);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        result = prime * result + ((tail == null) ? 0 : tail.hashCode());
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
        DirectedEdge other = (DirectedEdge) obj;
        if (head == null) {
            if (other.head != null)
                return false;
        } else if (!head.equals(other.head))
            return false;
        if (tail == null) {
            if (other.tail != null)
                return false;
        } else if (!tail.equals(other.tail))
            return false;
        if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
            return false;
        return true;
    }

}