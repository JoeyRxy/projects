package top.mine.website;

import org.junit.Test;

public class AppTest3 {

   

    @Test
    public void testStatic() {
        TestClass t1 = new TestClass(2, 3);
        TestClass t2 = new TestClass(1, 5);
        System.out.println(TestClass.getX());
        TestClass.setX(4);
        System.out.println(TestClass.getX());
        System.out.println(t1.getY());
        System.out.println(t2.getY());
    }

    enum A {
        HELLO,WORLD,THIS,SHIT
    }
    
    @Test
    public void testWHileTrue() {
        while(true);
    }

}