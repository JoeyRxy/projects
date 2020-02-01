package mine.learn.graphtheory.util;

import java.util.LinkedList;

/**
 * FibonacciHeap
 */
public class FibonacciHeap<E extends Comparable<? super E>> {

    class Node<T extends Comparable<? super T>> {
        T e;
        /** parent */
        Node<T> p;
        /** 双向链表表示的子节点 */
        LinkedList<Node<T>> children;

        boolean marked;

        public Node(T e, Node<T> p, boolean marked) {
            this.e = e;
            this.p = p;
            this.children = new LinkedList<>();
            this.marked = marked;
        }

    }

    /** heap size */
    private int n;
    LinkedList<Node<E>> rootList;

    public FibonacciHeap() {
        rootList = new LinkedList<>();

    }

    public void insert(E e) {
        rootList.add(new Node<E>(e, null, false));
    }

}