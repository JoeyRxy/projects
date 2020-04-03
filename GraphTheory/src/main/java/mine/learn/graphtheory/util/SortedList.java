package mine.learn.graphtheory.util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * SortedList
 */
@SuppressWarnings("unchecked")
public class SortedList<E extends Comparable<? super E>> {

    private E[] data;
    private int size;
    private Comparator<E> comparator;

    public SortedList(E[] set) {
        size = set.length;
        data = (E[]) new Comparable[size << 1];
        Arrays.sort(set);
        for (int i = 0; i < size; i++) {
            data[i] = set[i];
        }
    }

    public void add(E e) {
        if (size == data.length)
            resize();
        data[size] = e;
        int i = size;
        while (i != 0 && less(data[i], data[i - 1])) {
            swap(i, i - 1);
            i--;
        }
        size++;
    }

    public E get(int index) {
        return data[index];
    }

    public void change(int index, E e) {
        if (less(data[index], e)) {// 比原来大
            data[index] = e;
            index++;
            while (index != size && less(data[index], data[index - 1])) {
                swap(index, index - 1);
                index++;
            }
        } else {// 比原来小
            data[index] = e;
            while (index != 0 && less(data[index], data[index - 1])) {
                swap(index, index - 1);
                index--;
            }
        }
    }

    private void swap(int i, int j) {
        E e = data[i];
        data[i] = data[j];
        data[j] = e;
    }

    private boolean less(E e1, E e2) {
        if (comparator == null) {
            return e1.compareTo(e2) < 0;
        } else {
            return comparator.compare(e1, e2) < 0;
        }
    }

    private void resize() {
        E[] tmp = (E[]) new Comparable[size << 1];
        for (int i = 0; i < size; i++) {
            tmp[i] = data[i];
        }
        data = tmp;
    }
}