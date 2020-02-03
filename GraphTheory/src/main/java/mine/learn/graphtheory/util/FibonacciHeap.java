package mine.learn.graphtheory.util;

import java.util.Comparator;

/**
 * FibonacciHeap
 */
public class FibonacciHeap<E extends Comparable<? super E>> {

    class Node {
        E e;
        /** parent */
        Node p;
        /** 双向链表表示的子节点列表 */
        Node prev;
        Node next;

        boolean mark;

        public Node(E e, Node p, boolean mark) {
            this.e = e;
            this.p = p;
            this.mark = mark;
        }
    }

    /** heap size */
    private int n;
    private Node rootList;
    private Node min;
    private Comparator<? super E> comparator;

    public FibonacciHeap() {
        rootList = new Node();
    }

    public FibonacciHeap(Comparator<? super E> comparator) {
        rootList = new DoLinkList<>();
        this.comparator = comparator;
    }

    public void add(E e) {
        Node node = new Node(e, null, false);
        rootList.add(node);
        n++;
        if (min == null || less(node, min))
            min = node;
    }

    /**
     * remove specific value {@code e}
     */
    public E remove(E e) {
        return e;

    }

    public E poll() {
        Node z = min;
        if (z != null) {
            for (Node child : z.children) {
                rootList.add(child);
                child.p = null;
            }
            n--;
        }
        return z;

    }

    public E peek() {
        return null;

    }

    public boolean contains(E e) {
        return false;

    }

    public static <T extends Comparable<? super T>> FibonacciHeap<T> union(FibonacciHeap<T> h1, FibonacciHeap<T> h2) {
        FibonacciHeap<T> h = new FibonacciHeap<>();
        h1.union(h2);
        return h1;
    }

    public void union(FibonacciHeap<E> h) {
        if (comparator != h.comparator)
            throw new IllegalArgumentException("两个斐波那契堆的比较方式必须一样！");
        if (!less(min, h.min))
            min = h.min;
        DoLinkList<Node>.LinkNode tail = rootList.tail;
        DoLinkList<Node>.LinkNode hHead = h.rootList.head;
        tail.next = hHead;
        hHead.prev = tail;
        n += h.n;
    }

    public void consolidate() {

    }

    /**
     * whether {@code n1.e} less than {@code n2.e}.
     * 
     * @param n1
     * @param n2
     * @return whether {@code n1.e} less than {@code n2.e}
     */
    private boolean less(Node n1, Node n2) {
        if (comparator == null)
            return n1.e.compareTo(n2.e) < 0;
        else
            return comparator.compare(n1.e, n2.e) < 0;
    }

}