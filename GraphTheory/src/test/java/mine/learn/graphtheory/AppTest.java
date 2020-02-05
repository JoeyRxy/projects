package mine.learn.graphtheory;

import static org.junit.Assert.assertTrue;

import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.bean.WeightedEdge;
import mine.learn.graphtheory.util.PriorityQueueM;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class AppTest {

    /**
     * Pair
     */
    public class Pair implements Comparable<Pair> {

        Integer key;
        String val;

        @Override
        public int compareTo(Pair o) {
            return val.compareTo(o.val);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + Objects.hash(key);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            return Objects.equals(key, other.key);
        }

        private AppTest getEnclosingInstance() {
            return AppTest.this;
        }

        public Pair(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return String.format("Pair [%-5d : %s]", key, val);
        }

    }

    @Test
    public void testTopological() {
        assertTrue(true);
        LinkedList<Integer> list = new LinkedList<>();
    }

    @Test
    public void testPQM() {
        PriorityQueueM<Pair> pq = new PriorityQueueM<>(105);
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            pq.add(new Pair(r.nextInt(1000), randomString(r, r.nextInt(50) + 1)));
        }
        String[] t = new String[pq.size()];
        int idx = 0;
        while (!pq.isEmpty()) {
            String tmp = pq.poll().val;
            t[idx] = tmp;
            System.out.println(idx + " : " + tmp);
            idx++;
        }
        for (int i = 1; i < t.length; i++) {
            assertTrue("问题出在第 " + i + " 行", t[i].compareTo(t[i - 1]) >= 0);
        }
    }

    private String randomString(Random r, int len) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            builder.append((char) (r.nextInt(26) + 65));
        }
        return builder.toString();
    }

    @Test
    public void testHashCode() {
        int v = 2, w = 4;
        WeightedEdge e1 = new WeightedEdge(v, w, 1.0);
        WeightedEdge e2 = new WeightedEdge(w, v, 1.0);
        System.out.println(e1.hashCode() + " : " + e2.hashCode());
        System.out.println(e1.equals(e2));
        System.out.println(e1.compareTo(e2));
    }

    @Test
    public void testString() {
        String s1 = "hello";
        String s2 = "hello";
        System.out.println(s1 + s2);
    }
}
