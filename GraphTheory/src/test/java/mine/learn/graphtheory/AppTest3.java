package mine.learn.graphtheory;

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
}