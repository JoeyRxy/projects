package mine.learn.graphtheory.util;

import java.util.Iterator;

/**
 * DoLinkList
 */
public class DoLinkList<T> implements Iterable<T> {

    /**
     * Node
     */
    public class LinkNode {

        T val;
        LinkNode prev;
        LinkNode next;

        public LinkNode(T val, DoLinkList<T>.LinkNode lNode, DoLinkList<T>.LinkNode rNode) {
            this.val = val;
            this.prev = lNode;
            this.next = rNode;
        }

        public LinkNode() {
            prev = this;
            next = this;
            val = null;
        }
    }

    public LinkNode head;
    public LinkNode tail;

    public int size;

    public DoLinkList() {

    }

    public void add(T t) {
        if (head == null) {
            // IMPORTANT : 将新的对象用在constructor里能使用上吗？不能！
            // head = new Node(t, head, head);
            head = new LinkNode();
            head.val = t;
            tail = head;
            size++;
            return;
        }
        LinkNode node = new LinkNode(t, tail, head);
        tail.next = node;
        head.prev = node;
        tail = node;
        size++;
    }

    /**
     * 方法存疑
     * 
     * @param n
     */
    public void delete(DoLinkList<T>.LinkNode n) {
        if (n == null)
            return;
        if (n == head) {
            LinkNode next = head.next;
            tail.prev = next;
            next.prev = tail;
            head = next;
        } else if (n == tail) {
            LinkNode prev = tail.prev;
            prev.next = head;
            head.prev = prev;
            tail = prev;
        } else {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }
        size--;
    }

    /**
     * 
     * @param t
     * @return index where the value {@code t} first show up. {@code head} is 0.
     *         <p>
     *         -1 if it doesn't contain {@code t}
     */
    public int indexOf(T t) {
        LinkNode _t = head;
        for (int j = 0; j < size; j++) {
            if (_t.val == t)
                return j;
            _t = _t.next;
        }
        return -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoLinkListIterator();
    }

    /**
     * DoLinkListIterator
     */
    public class DoLinkListIterator implements Iterator<T> {
        LinkNode node = head;

        @Override
        public boolean hasNext() {
            return head != tail;
        }

        @Override
        public T next() {
            T t = node.val;
            node = node.next;
            return t;
        }

    }

}