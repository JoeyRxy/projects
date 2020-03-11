package mine.learn.graphtheory.computational_optimization;

class MyList {

    public Node head;
    public Node tail;

    public MyList() {
        head = new Node();
        tail = new Node();
        head.data = -1;
        tail.data = Integer.MAX_VALUE;
        head.next = tail;
        tail.prev = head;
    }

    public boolean isEmpty() {
        return head.next == tail;
    }

    public void add(int d) {
        Node node = new Node(d, tail.prev, tail);
        tail.prev.next = node;
        tail.prev = node;
    }

    public void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    @Override
    @Deprecated
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        if (head.next == tail) {
            builder.append("]");
            return builder.toString();
        }
        Node cur = head.next;
        while (true) {
            if (cur.next == tail) {
                builder.append(cur.data).append("]");
                return builder.toString();
            }
            builder.append(cur.data).append(", ");
            cur = cur.next;
        }
    }

    public void reset(Node cur) {
        cur.prev.next = cur;
        cur.next.prev = cur;
    }

}