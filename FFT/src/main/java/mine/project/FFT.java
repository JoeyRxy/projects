package mine.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FFT 实现了<b>同址计算</b> 目前支持数组长度<b>N</b>为2的幂次 主要方法为fft和ifft
 * 另一个辅助方法为trim(X)——将过小的数字视为0
 * 
 * recognize complex pattern:"-?\d+(\.\d+)? *[+-] *[ij] *\d+(\.\d+)? *"
 * 
 * test data: 1.4 +j, 2 ,-5 +j 7, 3.5 + j 2.1
 */
// @SuppressWarnings("unused")
public class FFT {
    final static double precision = 1E-5;

    /**
     * 另一种比较好的方法是通过类似 <b>dfs</b> 的方法（全排列！） see <b>DSP : P518</b>
     * 
     * @param N 将要进行转换数字总数
     * @return res 结果
     */
    private static int[] bitReverseOrder(int N) {
        int[] res = new int[N];
        int k = 1;
        for (int i = N >> 1; i != 0; i >>= 1) {
            res[i] = k;
            k <<= 1;
        }

        for (int i = 2; i < N; i <<= 1) {
            int base = res[i];
            for (int j = 1; j < i; j++) {
                res[j + i] = base + res[j];
            }
        }
        return res;
    }

    private static <T> void swap(T[] x, int i, int j) {
        T tmp = x[i];
        x[i] = x[j];
        x[j] = tmp;
    }

    private static void toIdenticalAdress(Complex[] x) {
        int N = x.length;
        int[] BRO = bitReverseOrder(N);// BRO -- BitReverseOrder
        for (int i = 0; i < N / 2; i++) {
            swap(x, i, BRO[i]);
        }
    }

    /**
     * 以2为底的对数
     * 
     * @param N 仅限N为2的幂次
     * @return log2(N)
     */
    private static int log2(int N) {
        int layer = 0;
        while (N != 1) {
            N >>= 1;
            layer++;
        }
        return layer;
    }

    /**
     * 判断是否是2的幂次
     * 
     * @param N
     * @return
     */
    public static boolean isPower2(int N) {
        // while (N != 1) {
        // if (N % 2 != 0)// 模运算很慢
        // return false;
        // N >>= 1;
        // }
        // return true;

        int tmp = 1;
        while (tmp < N) {
            tmp <<= 1;
            if (tmp == N)
                return true;
        }
        return false;
    }

    /**
     * 蝶形算子
     * <p>
     * <img src=
     * "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2d296589a18b87d6444fa34d6661435d/203fb80e7bec54e7a8e4cda6b9389b504fc26a7a.jpg"
     * />
     * 
     * @param X
     * @param i
     * @param j
     * @param N <code>W_N^l</code>
     * @param l <code>W_N^l</code>
     */
    private static void butterfly(Complex[] X, int i, int j, Complex W_N) {
        Complex tmpXi = X[i];
        Complex tmp = X[j].product(W_N);
        X[i] = X[i].plus(tmp);
        X[j] = tmpXi.minus(tmp);
    }

    /**
     * FFT对应于<code>double</code>数组
     * 
     * @param x 为{@code double}数组
     * @return
     */
    public static Complex[] fft(double[] x) {
        int N = x.length;
        Complex[] x_complex = new Complex[N];
        for (int i = 0; i < N; i++) {
            x_complex[i] = new Complex(x[i], 0);
        }
        return fft(x_complex);
    }

    /**
     * 非递归，同址计算
     * 
     * @param x
     * @return
     * @throws IllegalArgumentException 如果不为2的幂次的话
     */
    public static Complex[] fft(Complex[] x) {
        int N = x.length;
        if (!isPower2(N)) {
            throw new IllegalArgumentException("N must be power of 2");
        }
        toIdenticalAdress(x);
        int layer = log2(N);// 总层数

        Complex[] X = new Complex[N];
        for (int i = 0; i < N; i++) {
            X[i] = new Complex(x[i].re, x[i].im);
        }
        // 开始计算fft！
        int k = 2;
        int dist = N >> 1;
        for (int l = 0; l < layer; l++) {
            int i = 0;
            int group = N / k;
            while (i < group) {
                for (int m = 0; m < (k >> 1); m++) {
                    double expo = -2 * Math.PI * m * dist / N;
                    Complex W_N = new Complex(Math.cos(expo), Math.sin(expo));
                    butterfly(X, k * i + m, k * i + m + (k >> 1), W_N);
                }
                i++;
            }
            k <<= 1;
            dist >>= 1;
        }
        return X;
    }

    private static <T> void reverse(T[] x) {
        int n = x.length / 2;
        for (int i = 0; i < n; i++) {
            swap(x, i, i + n);
        }
    }

    /**
     * 
     * @param X
     * @return
     * @throws IllegalArgumentException
     */
    public static Complex[] ifft(Complex[] X) {
        int N = X.length;
        if (!isPower2(N)) {
            throw new IllegalArgumentException("N must be power of 2");
        }

        // for (int i = 0; i != N; i++)
        // X[i] = X[i].conjugate();
        // Complex[] x_r = fft(X);
        // for (int i = 0; i != N; i++) {
        // x_r[i] = x_r[i].conjugate();
        // x_r[i].re /= N;
        // x_r[i].im /= N;
        // }
        // return x_r;

        Complex[] x_r = fft(X);
        for (int i = 1, j = N - 1; i < j; i++, j--) {
            swap(x_r, i, j);
        }
        for (int i = 0; i < N; i++) {
            x_r[i].re /= N;
            x_r[i].im /= N;
        }
        return x_r;
    }

    /**
     * 将过小的数字记为0
     * 
     * @param X 如果X小于1E-10，记为0
     */
    private static void trim(Complex[] X) {
        for (int i = 0; i < X.length; i++) {
            if (Math.abs(X[i].re - (int) X[i].re) < precision)
                X[i].re = (int) X[i].re;
            if (Math.abs(X[i].im - (int) X[i].im) < precision)
                X[i].im = (int) X[i].im;
        }
    }

    /**
     * 圆周卷积
     * 
     * @param x
     * @param h
     * @return
     */
    public static Complex[] convolute(Complex[] x, Complex[] h) {
        int N = h.length;
        if (x.length != N)
            throw new IllegalArgumentException("长度不匹配");

        Complex[] X = fft(x);
        Complex[] H = fft(h);
        for (int i = 0; i < N; i++) {
            X[i] = X[i].product(H[i]);
        }
        return ifft(X);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 0 for FFT,Enter 1 for IFFT\nEnter `quit` to quit.");
        boolean useFFT;
        String start = scanner.nextLine();
        while (!start.equals("quit")) {
            if (start.charAt(0) == '0') {
                useFFT = true;
            } else if (start.charAt(0) == '1') {
                useFFT = false;
            } else {
                System.out.println("the input must be 0 or 1!");
                start = scanner.nextLine();
                continue;
            }

            System.out.println("put all in one line,delimited by comma");
            System.out.println("example:1 + j 2.3 , -2-j, 3.1,-j6");
            List<Complex> list = new ArrayList<>();
            String[] s = scanner.nextLine().split(",");
            for (var str : s) {
                list.add(Complex.complexParser(str));
            }

            // 为什么这种方式不行？貌似已经输入了 ^Z 所以已经判定了没有下一行，所以循环的最后就无法再次获得下一个输入了？
            // while (scanner.hasNextLine()) {
            // list.add(Complex.complexParser(scanner.nextLine()));
            // }
            int len = list.size();
            Complex[] x = new Complex[len];
            for (int i = 0; i < len; i++)
                x[i] = list.get(i);

            Complex[] X;
            if (useFFT) {
                X = FFT.fft(x);
                System.out.println("The fft result is:");
            } else {
                X = FFT.ifft(x);
                System.out.println("The ifft result is:");
            }

            int count = 0;
            for (var xval : X)
                System.out.println((count++) + " : " + xval);
            System.out.println();
            System.out.println("=============Finish!===============\n\nNext Calculate:");
            System.out.println("\nEnter 0 for FFT,Enter 1 for IFFT\nEnter `quit` for quit.");
            start = scanner.nextLine();
        }
        scanner.close();
    }
}