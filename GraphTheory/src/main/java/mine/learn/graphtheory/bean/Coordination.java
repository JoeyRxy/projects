package mine.learn.graphtheory.bean;

import java.util.Objects;

/**
 * Coordination
 */
public class Coordination implements Comparable<Coordination> {

    private double x, y;
    private double r, phi;

    public Coordination(double x, double y) {
        this.x = x;
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(x, y);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(x, y);
    }

    public double getR() {
        return r;
    }

    public double getPhi() {
        return phi;
    }

    @Override
    public int compareTo(Coordination o) {
        // TODO Auto-generated method stub
        return 0;
    }

    public double dist(Coordination goal) {
        double _x = x - goal.x;
        double _y = y - goal.y;
        return Math.sqrt(_x * _x + _y * _y);
    }

}