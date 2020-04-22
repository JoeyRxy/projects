package mine.learn.graphtheory;

import java.util.Iterator;
import java.util.LinkedList;

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
}