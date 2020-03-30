package top.mine.website.entity;

import java.util.Objects;

/**
 * MyFile
 */
public class Myfile {

    private int id;
    private int userId;
    private String fileName;

    public Myfile(int id, int userId, String fileName) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Myfile other = (Myfile) obj;
        return id == other.id;
    }

    public Myfile(int user_id, String fileName) {
        this.userId = user_id;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Myfile [fileName=" + fileName + ", id=" + id + ", userId=" + userId + "]";
    }

}