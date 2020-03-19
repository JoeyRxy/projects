package mine.learn.javawebajax.entity;

import java.util.Objects;

/**
 * Dr
 */
public class Doctor {
    private int id;
    private String name;
    private String about;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Doctor(int id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public Doctor() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doctor other = (Doctor) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Doctor [name=" + name + ", about=" + about + "]";
    }

}