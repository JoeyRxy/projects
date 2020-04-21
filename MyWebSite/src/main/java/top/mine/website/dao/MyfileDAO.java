package top.mine.website.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import top.mine.website.entity.Myfile;

/**
 * FileDAO
 */
public class MyfileDAO {
    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = MyfileDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(Myfile myfile) throws SQLException {
        if (exist(myfile))
            return;
        String sql = "INSERT INTO myfile(user_id, filename, fileLength) VALUES(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, myfile.getUserId());
        ps.setString(2, myfile.getFileName());
        ps.setLong(3, myfile.getFileLength());
        ps.execute();
        ps.close();
    }

    public static boolean exist(Myfile myfile) throws SQLException {
        String sql = "SELECT COUNT(*) FROM myfile WHERE user_id=? AND filename=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, myfile.getUserId());
        ps.setString(2, myfile.getFileName());
        ResultSet rs = ps.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        if (count == 0)
            return false;
        else
            return true;
    }

    public static void insert(Collection<Myfile> myfiles) throws SQLException {
        String sql = "INSERT INTO myfile(user_id,filename) VALUES(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (Myfile myfile : myfiles) {
            ps.setInt(1, myfile.getUserId());
            ps.setString(2, myfile.getFileName());
            ps.setLong(3, myfile.getFileLength());
            ps.execute();
        }
        ps.close();
    }

    public static void delete(Myfile file) throws SQLException {
        String sql = "DELETE FROM myfile WHERE user_id=? AND filename=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, file.getUserId());
        ps.setString(2, file.getFileName());
        ps.execute();
        ps.close();
    }

    public static void delete(Collection<Myfile> files) throws SQLException {
        String sql = "DELETE FROM myfile WHERE user_id=? AND filename=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (Myfile myfile : files) {
            ps.setInt(1, myfile.getUserId());
            ps.setString(2, myfile.getFileName());
            ps.execute();
        }
        ps.close();
    }

}