package mine.learn.javawebajax.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import mine.learn.javawebajax.entity.Doctor;

/**
 * DocDAO
 */
public class DoctorDAO {

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = DoctorDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean insert(final Doctor doctor) throws SQLException {
        if (nameExist(doctor.getName()))
            return false;
        final String sql = "INSERT INTO doctor(name,about) VALUES(?,?)";
        final PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, doctor.getName());
        pstmt.setString(2, doctor.getAbout());

        pstmt.execute();
        pstmt.close();
        return true;
    }

    public static boolean nameExist(String name) throws SQLException {
        final String sql = "SELECT * FROM doctor WHERE name = '" + name + "'";
        ResultSet res = conn.createStatement().executeQuery(sql);
        if (res.next())
            return true;
        else
            return false;

    }

    public static List<Doctor> all() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        final String sql = "SELECT * FROM doctor";
        ResultSet res = conn.createStatement().executeQuery(sql);
        while (res.next()) {
            list.add(new Doctor(res.getInt(1), res.getString(2), res.getString(3)));
        }
        res.close();
        return list;
    }

    public static Doctor queryByName(String name) throws SQLException {
        final String sql = "SELECT * FROM doctor WHERE name = '" + name + "'";
        ResultSet res = conn.createStatement().executeQuery(sql);
        if (res.next())
            return new Doctor(res.getInt(1), res.getString(2), res.getString(3));
        else
            return null;
    }
}