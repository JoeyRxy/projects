package mine.learn.javawebajax.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import mine.learn.javawebajax.entity.Doctor;

/**
 * DoctorDAOTest
 */
public class DoctorDAOTest {

    @Test
    public void testInsert() {
        try {
            DoctorDAO.insert(new Doctor(0, "zl", "zlzlzl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuery() throws SQLException {
        Doctor doc = DoctorDAO.queryByName("rxy");
        System.out.println(doc.getAbout());
    }

    @Test
    public void testAll() throws SQLException {
        List<Doctor> all = DoctorDAO.all();
        for (Doctor doctor : all) {
            System.out.println(doctor.getName() + " : " + doctor.getAbout());
        }
    }
}