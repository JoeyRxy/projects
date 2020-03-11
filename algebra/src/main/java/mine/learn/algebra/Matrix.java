package mine.learn.algebra;

import java.util.Arrays;

/**
 * Matrix
 */
@CoreTool(className = "mine.knowledge.Matrix")
public class Matrix {
    private final double epsilon = 1e-5;
    private double[][] data;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
    }

    public Matrix(int n) {
        data = new double[n][n];
    }

    public Matrix(Matrix m) {
        int[] size = m.size();
        data = new double[size[0]][];
        int newCols = size[1];
        for (int i = 0; i < data.length; i++) {
            data[i] = Arrays.copyOf(m.data[i], newCols);
        }
    }

    public Matrix(double[][] a) {
        int c = a[0].length;
        for (int i = 0; i < a.length; i++)
            data[i] = Arrays.copyOf(a[i], c);
    }

    public void initIdentityMatrix() {
        if (data.length != data[0].length)
            throw new IllegalArgumentException();
        for (int i = 0; i < data.length; i++) {
            data[i][i] = 1;
        }
    }

    public int[] size() {
        return new int[] { data.length, data[0].length };
    }

    public void randomInit(int multiple) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = (Math.random() - 0.5) * multiple;
            }
        }
    }

    public void set(int row, int col, double val) {
        data[row][col] = val;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public Matrix add(Matrix m) {
        if (!Arrays.equals(size(), m.size())) {
            throw new IllegalArgumentException();
        }
        int col = data[0].length;
        Matrix matrix = new Matrix(this);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < col; j++) {
                matrix.data[i][j] += m.data[i][j];
            }
        }
        return matrix;
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if (!Arrays.equals(m1.size(), m2.size())) {
            throw new IllegalArgumentException();
        }
        return m1.add(m2);
    }

    /**
     * O(n^3)
     * 
     * @param m
     * @return
     */
    public Matrix multiply(Matrix m) {
        if (data[0].length != m.data.length) {
            throw new IllegalArgumentException();
        }
        int newRows = data.length;
        int newCols = m.data[0].length;
        int _t = data[0].length;
        Matrix matrix = new Matrix(newRows, newCols);
        double[][] mData = matrix.data;
        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < newCols; j++) {
                for (int k = 0; k < _t; k++) {
                    mData[i][j] += data[i][k] * m.data[k][j];
                    if (is0(mData[i][j]))
                        mData[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    private boolean is0(double x) {
        if (Math.abs(x) < epsilon)
            return true;
        else
            return false;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.data[0].length != m2.data.length) {
            throw new IllegalArgumentException();
        }
        return m1.multiply(m2);
    }

    public Matrix reverse() {
        if (data.length != data[0].length)
            throw new IllegalArgumentException("row's num doesn't agree with col's.");

        Matrix m = new Matrix(data.length, data.length);
        m.initIdentityMatrix();
        lup = new LUP(this, m);
        if (lup.rank() != data.length)
            throw new IllegalArgumentException("奇异矩阵");
        return lup.eliminatedB();
    }

    /**
     * 行秩
     */
    public int rank() {
        if (lup != null)
            return lup.rank();
        return new LUP(this).rank();
    }

    public Matrix neg() {
        int[] size = size();
        int rows = size[0], cols = size[1];
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.data[i][j] = -data[i][j];
            }
        }
        return matrix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(data);
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
        Matrix other = (Matrix) obj;
        return Arrays.deepEquals(data, other.data);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[\n\t");
        for (int i = 0; i < data.length - 1; i++) {
            builder.append(arrayString(data[i])).append(",\n\t");
        }
        builder.append(arrayString(data[data.length - 1])).append("\n]");
        return builder.toString();
    }

    private String arrayString(double[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0;; i++) {
            b.append(String.format("%.3f", a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    private LUP lup;

}