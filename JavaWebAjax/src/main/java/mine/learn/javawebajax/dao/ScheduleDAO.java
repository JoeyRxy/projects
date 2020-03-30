package mine.learn.javawebajax.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import mine.learn.javawebajax.entity.DocSchedule;

/**
 * DayDAO
 */
public class ScheduleDAO {

    final public static byte start = 3;

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = ScheduleDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static List<DocSchedule> all(String table) throws SQLException {
        final String sql = "select t.doc_id,d.name,t.time1,t.time2,t.time3,t.time4,t.time5,t.time6,t.time7,t.time8 from "
                + table + " t, doctor d where t.doc_id = d.id";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        List<DocSchedule> list = new ArrayList<>();
        while (res.next()) {
            DocSchedule schedule = new DocSchedule(res.getInt(1), res.getString(2), res.getByte(3), res.getByte(4),
                    res.getByte(5), res.getByte(6), res.getByte(7), res.getByte(8), res.getByte(9), res.getByte(10));
            list.add(schedule);
        }
        stmt.close();
        res.close();
        return list;
    }

    public static void insert(String table, DocSchedule schedule) throws SQLException {
        final String sql = "INSERT INTO " + table
                + "(doc_id,time1,time2,time3,time4,time5,time6,time7,time8) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, schedule.getDoc_id());
        pstmt.setByte(2, schedule.getTime1());
        pstmt.setByte(3, schedule.getTime2());
        pstmt.setByte(4, schedule.getTime3());
        pstmt.setByte(5, schedule.getTime4());
        pstmt.setByte(6, schedule.getTime5());
        pstmt.setByte(7, schedule.getTime6());
        pstmt.setByte(8, schedule.getTime7());
        pstmt.setByte(9, schedule.getTime8());

        pstmt.execute();

        pstmt.close();
    }

    public static int decrease(String table, int doc_id, byte timei) throws SQLException {
        int x = query(table, doc_id, timei);
        if (x == 0)
            return 0;
        final String sql = "UPDATE " + table + " SET time" + timei + " = " + (x - 1) + " WHERE doc_id = " + doc_id;
        conn.createStatement().execute(sql);

        return x - 1;
    }

    public static int increase(String table, int doc_id, byte timei) throws SQLException {
        int surplus = query(table, doc_id, timei);
        if (surplus == start)
            return start;
        final String sql = "UPDATE " + table + " SET time" + timei + " = " + (surplus + 1) + " WHERE doc_id = "
                + doc_id;
        conn.createStatement().execute(sql);
        return surplus + 1;
    }

    public static void reset(String table, int doc_id, byte timei) throws SQLException {
        conn.createStatement()
                .execute("UPDATE " + table + " SET time" + timei + " = " + start + " WHERE doc_id = " + doc_id);
    }

    public static int query(String table, int doc_id, byte timei) throws SQLException {
        final String sql = "SELECT time" + timei + " FROM " + table + " WHERE doc_id = '" + doc_id + "'";
        ResultSet res = conn.createStatement().executeQuery(sql);
        if (res.next())
            return res.getInt(1);
        else
            throw new IllegalArgumentException("没有该医生（" + doc_id + ")");
    }
}