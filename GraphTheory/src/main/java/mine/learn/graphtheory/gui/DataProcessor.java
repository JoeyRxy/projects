package mine.learn.graphtheory.gui;

/**
 * DataProcessor
 */
public class DataProcessor {

    private byte[][][] map;
    /** [0, 9] */
    private final byte maxF = 9;

    public DataProcessor(byte[][][] map) {
        this.map = map;
    }

    public DataProcessor(int a, int b, int h) {
        map = new byte[a][][];
        for (int i = 0; i < a; i++) {
            map[i] = new byte[b][];
            for (int j = 0; j < b; j++) {
                map[i][j] = new byte[h];
            }
        }
    }

    public void validateF(byte f) {
        if (f < 0 || f > 9)
            throw new IllegalArgumentException("必须再阻力范围内");
    }

    public void set(int i, int j, int k, byte f) {
        validateF(f);
        map[i][j][k] = f;
    }
}