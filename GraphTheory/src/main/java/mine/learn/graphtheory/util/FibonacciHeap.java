package mine.learn.graphtheory.util;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * FibonacciHeap
 */
public class FibonacciHeap<E extends Comparable<? super E>> {

    static class Node<E> {
        E e;
        /** parent */
        Node<E> p;
        /** 双向链表表示的子节点 */
        LinkedList<Node<E>> children;

        boolean mark;

        public Node(E e, Node<E> p, boolean mark) {
            this.e = e;
            this.p = p;
            this.children = new LinkedList<>();
            this.mark = mark;
        }
    }

    /** heap size */
    private int n;
    private LinkedList<Node<E>> rootList;
    private Node<E> min;
    private Comparator<? super E> comparator;

    public FibonacciHeap() {
        rootList = new LinkedList<>();
    }

    public FibonacciHeap(Comparator<? super E> comparator) {
        rootList = new LinkedList<>();
        this.comparator = comparator;
    }

    public void add(E e) {
        Node<E> node = new Node<>(e, null, false);
        rootList.add(node);
        n++;
        if (min == null || less(node, min))
            min = node;
    }

    /**
     * remove specific value {@code e}
     */
    public E remove(E e) {

    }

    public E poll() {

    }

    public E peek() {

    }

    public boolean contains(E e) {
        return false;
    }

    public static <T extends Comparable<? super T>> FibonacciHeap<T> union(FibonacciHeap<T> h1, FibonacciHeap<T> h2) {
        FibonacciHeap<T> h = new FibonacciHeap<>();

        return h;
    }

    public void union(FibonacciHeap<E> h) {
        if (comparator != h.comparator)
            throw new IllegalArgumentException("两个斐波那契堆的比较方式必须一样！");
        if (!less(min, h.min))
            min = h.min;
        rootList.add
    }

    /**
     * whether {@code n1.e} less than {@code n2.e}.
     * 
     * @param n1
     * @param n2
     * @return whether {@code n1.e} less than {@code n2.e}
     */
    private boolean less(Node<E> n1, Node<E> n2) {
        if (comparator == null)
            return n1.e.compareTo(n2.e) < 0;
        else
            return comparator.compare(n1.e, n2.e) < 0;
    }

}