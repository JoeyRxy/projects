package mine.learn.graphtheory;

import java.util.Arrays;

class A {
    private static int[] a;
    private int n;

    public A(int n) {
        this.n = n;
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
    }

    public static void printA() {
        System.out.println(Arrays.toString(a));
    }

}