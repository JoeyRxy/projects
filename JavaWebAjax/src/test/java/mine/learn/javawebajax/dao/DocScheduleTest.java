package mine.learn.javawebajax.dao;

import java.sql.SQLException;

import org.junit.Test;

import mine.learn.javawebajax.entity.DocSchedule;

/**
 * DocScheduleTest
 */
public class DocScheduleTest {

    public void testInsert() throws SQLException {
        ScheduleDAO.insert("day2", new DocSchedule(1));
        ScheduleDAO.insert("day2", new DocSchedule(2));
        ScheduleDAO.insert("day2", new DocSchedule(3));
        ScheduleDAO.insert("day2", new DocSchedule(4));
        ScheduleDAO.insert("day3", new DocSchedule(1));
        ScheduleDAO.insert("day3", new DocSchedule(2));
        ScheduleDAO.insert("day3", new DocSchedule(3));
        ScheduleDAO.insert("day3", new DocSchedule(4));

        ScheduleDAO.insert("day4", new DocSchedule(1));
        ScheduleDAO.insert("day4", new DocSchedule(2));
        ScheduleDAO.insert("day4", new DocSchedule(3));
        ScheduleDAO.insert("day4", new DocSchedule(4));

        ScheduleDAO.insert("day5", new DocSchedule(1));
        ScheduleDAO.insert("day5", new DocSchedule(2));
        ScheduleDAO.insert("day5", new DocSchedule(3));
        ScheduleDAO.insert("day5", new DocSchedule(4));
    }

    @Test
    public void testQuery() throws SQLException {
        int query = ScheduleDAO.query("day3", 1, (byte) 2);
        System.out.println(query);
    }

    @Test
    public void testDecrease() throws SQLException {
        int count = ScheduleDAO.decrease("day3", 1, (byte) 3);
        System.out.println(count);
    }
}