package mine.learn.graphtheory.util;

import java.util.Comparator;
import java.util.Iterator;
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
        this.comparator = comparator;
        keys = (E[]) new Comparable[maxCapacity + 1];
        pq = new int[maxCapacity + 1];
        qp = new int[maxCapacity + 1];
        for (int i = 0; i < maxCapacity + 1; i++)
            qp[i] = -1;
    }

    /**
     * most important diff from {@link PriorityQueue}:
     * <ul>
     * <li>bind an unique index <code>i</code> with element <code>e</code>, in order
     * to quick-find</li>
     * <li>you should remember which index <code>i</code> bind with which element
     * <code>e</code></li>
     * </ul>
     *
     * @param k bind index <code>i</code> with element <code>e</code>
     * @param e
     */
    public void add(int k, E e) {
        if (contains(k))
            changeKey(k, e);

        n++;
        pq[n] = k;
        qp[k] = n;
        keys[k] = e;
        siftUp(n);
    }

    public E peek() {
        return keys[pq[1]];
    }

    public int poll() {
        int k = pq[1];
        exch(1, n--);
        siftDown(1);
        keys[k] = null;
        qp[k] = -1;
        return k;
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
        int x = pq[k];
        E e = keys[x];
        exch(k, n--);
        siftDown(k);

        qp[k] = -1;// IMPORTANT 这一行的位置很关键，要保证qp具有如下性质：qp[pq[i]]=i/-1(当pq[i]指示的元素keys[pq[i]]不存在的时候)
        keys[x] = null;
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

    // helpers

    /**
     * @param k
     */
    private void siftUp(int k) {
        while (less(k, k >> 1)) {
            exch(k, k >> 1);
            k >>= 1;
            if (k == 1)
                return;
        }
    }

    /**
     * 注意保证最后一步要能够到底
     *
     * @param k
     */
    private void siftDown(int k) {
        while ((k << 1) <= n) {
            int j = k << 1;
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

    private void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
}