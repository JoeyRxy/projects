package mine.learn.graphtheory.util;

import java.util.Comparator;

/**
 * PriorityQueuePublic
 */
@SuppressWarnings("unchecked")
public class PriorityQueueExt<E extends Comparable<? super E>> {

    private E[] keys;
    private int n;
    private Comparator<E> comparator;

    public PriorityQueueExt(final int initCapacity) {
        keys = (E[]) new Comparable[initCapacity + 1];
    }

    public PriorityQueueExt(final int initCapacity, final Comparator<E> comparator) {
        this(initCapacity);
        this.comparator = comparator;
    }

    public PriorityQueueExt() {
        this(1);
    }

    public PriorityQueueExt(final Comparator<E> comparator) {
        this(1, comparator);
    }

    /**
     * IMPORTANT 精妙！
     * <p>
     * add也具有修改的作用，当“以equals &
     * hashCode”为标志的元素<code>e</code>已经存在于pq中时，就会修改以“comparable 或
     * comparator”作为<strong>比较</strong>的标志的元素
     * 
     * @param e
     */
    public void add(final E e) {
        if (n == keys.length - 1)
            resize(2 * n + 2);
        keys[++n] = e;
        siftUp(n);
    }

    public E peek() {
        return keys[1];
    }

    public E poll() {
        final E e = keys[1];
        exch(1, n--);
        keys[n + 1] = null;
        siftDown(1);
        return e;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void change(int idx, E e) {
        keys[idx] = e;
        siftUp(idx);
        siftDown(idx);
    }

    public E get(int idx) {
        return keys[idx];
    }

    // helpers
    private void siftUp(int i) {
        while (i > 1 && less(i, i >> 1)) {
            exch(i, i >> 1);
            i >>= 1;
        }
    }

    private void siftDown(int i) {
        // <=很关键
        while ((i << 1) <= n) {
            int j = i << 1;
            if (j < n && !less(j, j + 1))
                j = j + 1;
            if (!less(j, i))
                break;
            exch(i, j);
            i = j;
        }
    }

    private void exch(final int i, final int j) {
        final E tmp = keys[i];
        keys[i] = keys[j];
        keys[j] = tmp;
    }

    private boolean less(final int i, final int j) {
        if (comparator == null)
            return keys[i].compareTo(keys[j]) < 0;
        else
            return comparator.compare(keys[i], keys[j]) < 0;

    }

    private void resize(final int capacity) {
        assert capacity > n;
        final E[] tmp = (E[]) new Comparable[capacity];
        for (int i = 0; i < n; i++)
            tmp[i + 1] = keys[i + 1];
        keys = tmp;
    }

}