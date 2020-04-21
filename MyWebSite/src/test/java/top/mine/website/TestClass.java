package top.mine.website;

public class TestClass {

    static{
        System.out.println("Hello Static!");
    }

    static int x;
    int y;
    public TestClass(int x2,int y) {
        TestClass.x = x2;
        this.y = y;
    }
    
    /**
     * @return the x
     */
    public static int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param x the x to set
     */
    public static void setX(int x2) {
        x = x2;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
}