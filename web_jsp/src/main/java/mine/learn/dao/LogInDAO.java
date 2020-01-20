package mine.learn.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import mine.learn.entity.LoginInf;

public class LogInDAO {

    private static PreparedStatement pstmt;
    private static ResultSet resultSet;
    private static DataSource dataSource;

    static {
        InputStream inputStream;
        Properties properties = new Properties();
        try {
            inputStream = LogInDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean check(LoginInf info) throws Exception {
        Connection conn = dataSource.getConnection();

        final String sql = "SELECT pwd FROM sunck.myapp_students where sname = ?";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, info.getUname());
        resultSet = pstmt.executeQuery();
        String pwd = null;
        if (resultSet.next()) {
            pwd = resultSet.getString("pwd");
        }
        assert pwd != null;

        resultSet.close();
        // 不要频繁关闭连接

        if (pwd.equals(info.getUpwd()))
            return true;
        else
            return false;

    }
}