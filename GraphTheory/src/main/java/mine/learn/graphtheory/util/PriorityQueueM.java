package mine.learn.graphtheory.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * PriorityQueueM
 * <p>
 * 元素唯一
 * </p>
 */
@SuppressWarnings("unchecked")
public class PriorityQueueM<E extends Comparable<? super E>> {

    /**
     * 记录元素在数组中的位置
     */
    private Map<E, Integer> qpMap;
    private E[] keys;
    private int n;
    private Comparator<E> comparator;

    public PriorityQueueM(final int maxCapacity) {
        keys = (E[]) new Comparable[maxCapacity + 1];
        qpMap = new HashMap<>(maxCapacity + 1);
    }

    public PriorityQueueM(final int maxCapacity, final Comparator<E> comparator) {
        this(maxCapacity);
        this.comparator = comparator;
    }

    public PriorityQueueM() {
        this(1);
    }

    public PriorityQueueM(final Comparator<E> comparator) {
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
        final Integer idx = qpMap.get(e);
        if (idx != null) {
            final E before = keys[idx];
            keys[idx] = e;
            if (less(before, e))
                siftDown(idx);
            else
                siftUp(idx);
            return;
        }
        if (n == keys.length - 1)
            resize(2 * n + 2);
        keys[++n] = e;
        qpMap.put(e, n);
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
        qpMap.remove(e);
        return e;
    }

    public void remove(final E e) {
        final Integer idx = qpMap.get(e);
        if (idx == null)
            return;
        exch(idx, n--);
        keys[n + 1] = null;
        qpMap.remove(e);
        siftDown(idx);
    }

    public void changeKey(final E before, final E e) {
        final Integer idx = qpMap.get(before);
        if (idx == null)
            return;
        if (keys[idx].equals(e))
            return;
        keys[idx] = e;
        qpMap.remove(before);
        qpMap.put(e, idx);
        if (less(before, e))
            siftDown(idx);
        else
            siftUp(idx);
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(final E e) {
        return qpMap.containsKey(e);
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
        qpMap.put(keys[i], i);
        qpMap.put(keys[j], j);
    }

    private boolean less(final int i, final int j) {
        if (comparator == null)
            return keys[i].compareTo(keys[j]) < 0;
        else
            return comparator.compare(keys[i], keys[j]) < 0;

    }

    private boolean less(final E e1, final E e2) {
        if (comparator == null)
            return e1.compareTo(e2) < 0;
        else
            return comparator.compare(e1, e2) < 0;

    }

    private void resize(final int capacity) {
        assert capacity > n;
        final E[] tmp = (E[]) new Comparable[capacity];
        for (int i = 0; i < n; i++)
            tmp[i + 1] = keys[i + 1];
        keys = tmp;
    }

}