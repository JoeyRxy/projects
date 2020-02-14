package mine.learn.graphtheory.bean;

import java.util.Objects;

/**
 * Coordination
 */
public class Coordination implements Comparable<Coordination> {

    private int x;
    private int y;
    private int z;

    public Coordination(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int dist(Coordination other) {
        return Math.abs(other.x - x) + Math.abs(other.z - z) + Math.abs(other.y - y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
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
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int compareTo(Coordination o) {
        if (x > o.x)
            return 1;
        else if (x == o.x) {
            if (y > o.y)
                return 1;
            else if (y == o.y) {
                if (z > o.z)
                    return 1;
                else if (z == o.z)
                    return 0;
                else
                    return -1;
            } else
                return -1;
        } else
            return -1;
    }

}