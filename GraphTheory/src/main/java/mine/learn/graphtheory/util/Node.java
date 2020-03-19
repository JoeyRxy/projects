package mine.learn.graphtheory.util;

import java.util.Objects;

public class Node {
    public Node prev;
    public Node next;
    public int data = Integer.MIN_VALUE;

    public Node() {
    }

    /**
     * 
     * @param data
     * @param prev
     * @param next
     */
    public Node(int data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        return Integer.toString(data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, next, prev);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return data == other.data && Objects.equals(next, other.next) && Objects.equals(prev, other.prev);
    }

}