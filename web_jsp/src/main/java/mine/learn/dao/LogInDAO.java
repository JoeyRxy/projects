package mine.learn.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mysql.cj.log.Log;

import mine.learn.entity.LoginInf;

public class LogInDAO {

    private static PreparedStatement pstmt;
    private static Connection conn;
    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = LogInDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param info
     * @return <code>true</code>插入一条数据；<code>false</code>该用户已存在
     * @throws Exception
     */
    public static boolean insert(LoginInf info) throws Exception {
        if (is(info.getUname())) {
            return false;
        }
        final String sql = "INSERT INTO users(name,pwd,mobile) VALUES(?,?,?)";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, info.getUname());
        pstmt.setString(2, info.getUpwd());
        pstmt.setString(3, info.getUmobile());

        pstmt.execute();
        return true;
    }

    public static boolean is(String name) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM users where name = '" + name + "'";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        int count = -1;
        if (res.next())
            count = res.getInt(1);
        res.close();
        stmt.close();
        if (count == 1)
            return true;
        else if (count == 0)
            return false;
        else
            throw new SQLException("数据库出现错误，\"" + name + "\" 出现了 " + count + " 次！");
    }

    public static boolean check(LoginInf info) throws Exception {

        final String sql = "SELECT pwd FROM sunck.myapp_students where sname = ?";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, info.getUname());
        ResultSet resultSet = pstmt.executeQuery();
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