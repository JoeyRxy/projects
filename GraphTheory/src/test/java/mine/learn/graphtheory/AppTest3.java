package mine.learn.graphtheory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class AppTest3 {

    @Test
    public void testStaticInit() {
        A.printA();
        A a = new A(5);
        A.printA();
        A b = new A(9);
        A.printA();
    }

    @Test
    public void testContainer() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()) {
            Integer i = iter.next();
            list.remove(i);
            System.out.println(i);
        }
    }

    @Test
    public void testExpm1() {
        System.out.println(Math.expm1(0));
        System.out.println(Math.expm1(1));
    }

    @Test
    public void testToArray() {
        List<Integer> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(i + 100);
        }
        // Integer[] array = list.toArray(new Integer[0]);
        // for (Integer integer : array) {
        // System.out.println(integer);
        // }
        Integer[] a = (Integer[]) list.toArray();
        for (Integer i : a) {
            System.out.println(i);
        }
    }

    @Test
    public void testHashMap() {
        Random r = new Random(System.currentTimeMillis());
        HashMap<Double, Integer> map = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double randx = r.nextGaussian() * 10;
            map.put(randx, Double.hashCode(randx));
        }
        Integer[] res = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            res[i] = map.get(r.nextGaussian() * 10);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start));
        // System.out.println(Arrays.toString(res));
    }

    class BB {
        int x, y;

        BB(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    void f(BB a) {
        System.out.println(a.x + " : " + a.y);
        a.x = 100000;
    }

    @Test
    public void testA() {
        BB a = new BB(20, 10);
        System.out.println(a.x);
    }

    @Test
    public void testAtan() {
        System.out.println(Math.atan2(1, 0) / Math.PI);
    }
}