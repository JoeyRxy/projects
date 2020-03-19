package mine.learn.javawebajax.entity;

import java.util.Objects;

import mine.learn.javawebajax.dao.ScheduleDAO;

/**
 * DocSchedule
 */
public class DocSchedule {

    private int doc_id;
    private String name;
    private byte time1;
    private byte time2;
    private byte time3;
    private byte time4;
    private byte time5;
    private byte time6;
    private byte time7;
    private byte time8;

    @Override
    public int hashCode() {
        return Objects.hash(doc_id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DocSchedule other = (DocSchedule) obj;
        return doc_id == other.doc_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public byte getTime1() {
        return time1;
    }

    public void setTime1(byte time1) {
        this.time1 = time1;
    }

    public byte getTime2() {
        return time2;
    }

    public void setTime2(byte time2) {
        this.time2 = time2;
    }

    public byte getTime3() {
        return time3;
    }

    public void setTime3(byte time3) {
        this.time3 = time3;
    }

    public byte getTime4() {
        return time4;
    }

    public void setTime4(byte time4) {
        this.time4 = time4;
    }

    public byte getTime5() {
        return time5;
    }

    public void setTime5(byte time5) {
        this.time5 = time5;
    }

    public byte getTime6() {
        return time6;
    }

    public void setTime6(byte time6) {
        this.time6 = time6;
    }

    public byte getTime7() {
        return time7;
    }

    public void setTime7(byte time7) {
        this.time7 = time7;
    }

    public byte getTime8() {
        return time8;
    }

    public void setTime8(byte time8) {
        this.time8 = time8;
    }

    public DocSchedule(int doc_id) {
        this.doc_id = doc_id;
        byte start = ScheduleDAO.start;
        time1 = start;
        time2 = start;
        time3 = start;
        time4 = start;
        time5 = start;
        time6 = start;
        time7 = start;
        time8 = start;
    }

    public DocSchedule(int doc_id, String name, byte time1, byte time2, byte time3, byte time4, byte time5, byte time6,
            byte time7, byte time8) {
        this.doc_id = doc_id;
        this.name = name;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
    }

    public DocSchedule() {
    }
}