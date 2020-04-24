package mine.learn.graphtheory.bean;

import java.util.Objects;

/**
 * Coordination
 */
public class Coordination {

    private double x, y;
    private double r, phi;

    public Coordination(double x, double y) {
        this.x = x;
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(y, x);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(y, x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        phi = Math.atan2(y, x);
    }

    public double getR() {
        return r;
    }

    public double getPhi() {
        return phi;
    }

    public double dist(Coordination goal) {
        double _x = x - goal.x;
        double _y = y - goal.y;
        return Math.sqrt(_x * _x + _y * _y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordination other = (Coordination) obj;
        return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
                && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
    }

    @Override
    public String toString() {
        return String.format("Polar(%5.2f, %5.2f PI), Cartesian(%5.2f, %5.2f)", r, phi / Math.PI, x, y);
    }

    public Coordination relativeCoordinationOf(Coordination coordination) {
        return new Coordination(x - coordination.x, y - coordination.y);
    }

}