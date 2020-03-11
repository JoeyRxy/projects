package mine.learn.algebra;

/**
 * LUP
 */
public class LUP {
    private final double epsilon = 1e-5;
    private double[][] a;
    private Matrix B;

    public LUP(Matrix A, Matrix B) {
        this.B = B;
        int[] sizeA = A.size();
        int[] sizeB = B.size();
        int colsA = sizeA[1];
        int colsB = sizeB[1];
        if (sizeA[0] != sizeB[0])
            throw new IllegalArgumentException();

        a = new double[sizeA[0]][colsA + colsB];
        for (int i = 0; i < a.length; i++) {
            int j;
            for (j = 0; j < colsA; j++)
                a[i][j] = A.get(i, j);

            for (; j < a[0].length; j++)
                a[i][j] = B.get(i, j - colsA);
        }
        for (int j = 0, i = 0; j < colsA && i < a.length; j++, i++) {
            // 找列j中的最大值
            int maxRow = maxRowOf(i, j);
            if (is0(a[maxRow][j]))
                continue;
            // swap行
            swap(i, maxRow);
            // uniform行
            uniform(i, j);
            // pivot
            pivot(i, j);
        }
    }

    public LUP(Matrix A) {
        int[] size = A.size();
        int colsA = size[1];
        a = new double[size[0]][colsA];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < colsA; j++)
                a[i][j] = A.get(i, j);

        for (int j = 0, i = 0; j < colsA && i < a.length; j++, i++) {
            // 找列j中的最大值
            int maxRow = maxRowOf(i, j);
            if (is0(a[maxRow][j]))
                continue;
            // swap行
            swap(i, maxRow);
            // uniform行
            uniform(i, j);
            // pivot
            pivot(i, j);
        }
    }

    /**
     * 
     * @return rank of <code>A</code> and the argument matrix <code>a</code>.
     */
    public int rank() {
        int tmp = a[0].length - B.size()[1] - 1;
        int i = a.length - 1;
        while (is0(a[i][tmp])) {
            i--;
        }
        return i + 1;
    }

    private void pivot(int row, int startCol) {
        assert is0(a[row][startCol] - 1);
        for (int i = 0; i < a.length; i++) {
            if (i == row)
                continue;
            double x = a[i][startCol];
            for (int j = startCol; j < a[0].length; j++) {
                a[i][j] -= x * a[row][j];
                if (is0(a[i][j]))
                    a[i][j] = 0;
            }
        }
    }

    private int maxRowOf(int startRow, int j) {
        int maxi = startRow;
        for (int i = startRow; i < a.length; i++)
            if (!is0(a[i][j]) && a[i][j] > a[maxi][j])
                maxi = i;
        return maxi;
    }

    private void uniform(int row, int startCol) {
        double u = a[row][startCol];
        a[row][startCol] = 1;
        for (int j = startCol + 1; j < a[0].length; j++)
            a[row][j] /= u;
    }

    private boolean is0(double x) {
        if (Math.abs(x) < epsilon)
            return true;
        else
            return false;
    }

    private void swap(int row1, int row2) {
        double[] tmp = a[row1];
        a[row1] = a[row2];
        a[row2] = tmp;
    }

    public Matrix eliminatedArgumentMatrix() {
        return new Matrix(a);
    }

    public Matrix eliminatedB() {
        int[] size = B.size();
        int colsA = a[0].length - size[1];
        double[][] t = new double[size[0]][size[1]];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = a[i][j + colsA];
            }
        }
        return new Matrix(t);
    }

}