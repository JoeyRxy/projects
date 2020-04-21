package top.mine.website.entity;

import java.util.Objects;

/**
 * MyFile
 */
public class Myfile {

    private int userId;
    private String fileName;
    private long fileLength;

    public Myfile(int userId, String fileName, long fileLength) {
        this.userId = userId;
        this.fileName = fileName;
        setFileLength(fileLength);
    }

    public Myfile(int userId, String fileName) {
        this.userId = userId;
        this.fileName = fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, userId);
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
        return Objects.equals(fileName, other.fileName) && userId == other.userId;
    }

    @Override
    public String toString() {
        return "Myfile [fileLength=" + fileLength + ", fileName=" + fileName + ", userId=" + userId + "]";
    }

}