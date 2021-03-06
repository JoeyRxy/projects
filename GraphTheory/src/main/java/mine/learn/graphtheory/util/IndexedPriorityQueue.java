package mine.learn.graphtheory.util;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * IndexedPriorityQueue
 * <p>
 * 这是一个支持在<strong>指定不重复位置</strong>插入元素的优先队列；
 *
 * <p>
 * 优点和应用包括：
 * <ul>
 * <li>contains的O(1)实现</li>
 * <li>remove(E e)的O(log n)实现</li>
 * </ul>
 * <p>
 * <p>
 * 需要注意的性质有三点：
 * <ol>
 * <li>对于外界来说，一个索引<code>idx</code>和值<code>e</code>组成了一个不变的<strong>元组</strong>，在指定索引的时候就表示了这个值</li>
 * <li>插入的时候两个参数：索引<code>idx</code>永远连着元素<code>key</code>；删除掉元素的同时也就删除了该索引</li>
 * <li>qp[pq[i]] = i/-1(当<code>keys[pq[i]]</code>不存在)</li>
 * </ol>
 * 上边这几条确定了方法add,remove,change的实现！很关键
 */
@SuppressWarnings("unchecked")
public class IndexedPriorityQueue<E extends Comparable<? super E>> {

    private Comparator<E> comparator;
    private int n;
    private int[] pq;
    private int[] qp;
    private E[] keys;

    public IndexedPriorityQueue(int maxCapacity) {
        keys = (E[]) new Comparable[maxCapacity + 1];
        pq = new int[maxCapacity + 1];
        qp = new int[maxCapacity + 1];
        for (int i = 0; i < maxCapacity + 1; i++)
            qp[i] = -1;
    }

    public IndexedPriorityQueue(int maxCapacity, Comparator<E> comparator) {
        this(maxCapacity);
        this.comparator = comparator;
    }

    /**
     * most important diff from {@link PriorityQueue}:
     * <ul>
     * <li>bind an unique index <code>k</code> with element <code>e</code>, in order
     * to quick-find</li>
     * <li>you should remember which index <code>k</code> bind with which element
     * <code>e</code></li>
     * <li>if <code>k</code> exists, change the key to <code>e</code>, else add the
     * <code>k</code> with element <code>e</code>.</li>
     * </ul>
     * <p>
     * <code>k</code> start from 0.
     * 
     * @param k bind index <code>k</code> with element <code>e</code>
     * @param e
     */
    public void add(int k, E e) {
        if (contains(k)) {
            changeKey(k, e);
            return;
        }
        n++;
        pq[n] = k;
        qp[k] = n;
        keys[k] = e;
        siftUp(n);
    }

    public int peek() {
        return pq[1];
    }

    public E peekKey() {
        return keys[pq[1]];
    }

    public int poll() {
        int min = pq[1];
        exch(1, n--);
        siftDown(1);
        keys[min] = null;
        qp[min] = -1;
        return min;
    }

    /**
     * 你应该知道元素<code>key</code>的索引。 如果不然，就使用另一个类{@link PriorityQueueM}
     *
     * @param key
     * @return
     */
    @Deprecated(since = "耗时O(n)")
    public E remove(E key) {
        for (int i = 1; i <= n; i++)
            if (keys[i] == key)
                return remove(qp[i]);

        throw new NoSuchElementException(key + "不存在");
    }

    /**
     * 删除{@code keys[pq[k]]}
     *
     * @param k
     * @return
     */
    public E remove(int k) {
        if (!contains(k))
            return null;
        int i = qp[k];
        E e = keys[k];
        exch(i, n--);
        siftUp(i);
        siftDown(i);

        qp[k] = -1;// IMPORTANT 这一行的位置很关键，要保证qp具有如下性质：qp[pq[i]]=i/-1(当pq[i]指示的元素keys[pq[i]]不存在的时候)
        keys[k] = null;
        return e;
    }

    /**
     * change the key bound to index <code>i</code> to <code>key</code>
     * 
     * @param k
     * @param key
     */
    public void changeKey(int k, E key) {
        if (!contains(k))
            return;
        if (keys[k].equals(key))
            return;

        keys[k] = key;
        siftUp(qp[k]);
        siftDown(qp[k]);
    }

    public void decreaseKey(int k, E key) {
        assert less(key, keys[k]);
        keys[k] = key;
        siftUp(qp[k]);
    }

    /**
     * 是否存在索引<code>i</code>
     *
     * @param i
     * @return
     */
    public boolean contains(int i) {
        return qp[i] != -1;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    @Override
    public String toString() {
        if (n == 0)
            return "[]";
        StringBuilder builder = new StringBuilder("[");
        builder.append(keys[pq[1]]);
        for (int i = 1; i < n; i++) {
            builder.append(", " + keys[pq[i + 1]]);
        }
        builder.append("]");
        return builder.toString();
    }

    // helpers

    /**
     * @param k
     */
    private void siftUp(int k) {
        while (k > 1 && less(k, k >> 1)) {
            exch(k, k >> 1);
            k >>= 1;
        }
    }

    /**
     * 注意保证最后一步要能够到底
     *
     * @param k
     */
    private void siftDown(int k) {
        int j;
        while ((j = k << 1) <= n) {
            if (j < n && less(j + 1, j))
                j++;
            if (less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator != null)
            return comparator.compare(keys[pq[i]], keys[pq[j]]) < 0;
        else
            return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private boolean less(E e1, E e2) {
        if (comparator == null)
            return e1.compareTo(e2) < 0;
        else
            return comparator.compare(e1, e2) < 0;
    }

    private void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
}