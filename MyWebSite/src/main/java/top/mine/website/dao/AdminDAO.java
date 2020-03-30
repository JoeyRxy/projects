package top.mine.website.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import top.mine.website.util.BCrypt;

/**
 * AdminDAO
 */
public class AdminDAO {

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = AdminDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean check(String adminname, String adminpwd) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT adminpwd FROM admin WHERE adminname = '" + adminname + "'");
        String pwd = null;
        if (rs.next()) {
            pwd = rs.getString(1);
        }
        rs.close();
        stmt.close();
        if (pwd == null)
            return false;
        return BCrypt.checkpw(adminpwd, pwd);
    }
}