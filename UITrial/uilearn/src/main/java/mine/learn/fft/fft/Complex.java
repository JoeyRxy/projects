package mine.learn.fft.fft;

public class Complex {

    public double re;
    public double im;

    public Complex() {
        this.re = 0;
        this.im = 0;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     *
     * @param str recognize complex Pattern:"-?\d+(\.\d+)? *[+-] *[ij] *\d+(\.\d+)?
     *            *"
     * @return
     */
    public static Complex complexParser(String str) {
        String x, y;
        str = " " + str + " ";
        String[] res = str.split("[ij]");
        if (res.length == 1)
            return new Complex(Double.parseDouble(res[0]), 0);
        x = res[0];
        y = res[1];
        int i = x.length() - 1;
        double xval, yval;
        if (containDigit(y))
            yval = Double.parseDouble(y);
        else
            yval = 1;

        if (containDigit(x)) {
            while (x.charAt(i) == ' ')
                i--;
            xval = Double.parseDouble(x.substring(0, i));
        } else {
            xval = 0;
        }
        if (x.charAt(i) == '-')
            yval = -yval;
        return new Complex(xval, yval);
        // int i = 0, j = x.length() - 1;
        // char ci = x.charAt(i), cj = x.charAt(j);
        // while (ci == ' ') {
        // i++;
        // ci = x.charAt(i);
        // }
        // while (cj == ' ') {
        // j--;
        // cj = x.charAt(j);
        // }
        // double xval, yval;
        // if (ci == '-')
        // xval = -Double.parseDouble(x.substring(i + 1, j));
        // else
        // xval = Double.parseDouble(x.substring(i, j));
        // if (hasDigit(y))
        // yval = Double.parseDouble(y);
        // else
        // yval = 1;

        // if (cj == '-')
        // yval = -yval;
        // return new Complex(xval, yval);
    }

    private static boolean containDigit(String s) {
        int n = s.length();
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (c >= '0' && c <= '9')
                return true;
        }
        return false;
    }

    /**
     * 复数乘法
     *
     * @param o
     * @return
     */
    public Complex product(Complex o) {
        Complex res = new Complex(o.re * this.re - o.im * this.im, o.re * this.im + o.im * this.re);
        return res;
    }

    /**
     * 加法
     *
     * @param o
     * @return
     */
    public Complex plus(Complex o) {
        Complex res = new Complex(o.re + this.re, o.im + this.im);
        return res;
    }

    /**
     * 减法
     *
     * @param o
     * @return
     */
    public Complex minus(Complex o) {
        Complex res = new Complex(this.re - o.re, this.im - o.im);
        return res;
    }

    /**
     * X的共轭
     *
     * @return X的共轭
     */
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    @Override
    public String toString() {
        String s;
        if (im < 0) {
            if (re == 0)
                s = String.format("- j %.4f", -im);
            else
                s = String.format("%.4f - j %.4f", re, -im);
        } else if (im == 0)
            s = String.format("%.4f", re);
        else if (re == 0)
            s = String.format("j %.4f", im);
        else
            s = String.format("%.4f + j %.4f", re, im);
        return s;
    }
}
// 1+j 4 j 5 7-j5