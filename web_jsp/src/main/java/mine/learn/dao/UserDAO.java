package mine.learn.dao;

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

import mine.learn.entity.UserInf;

public class UserDAO {

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = UserDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param info Class {@code UserInf}
     * @return 插入{@code info}
     * @throws SQLException
     */
    public static boolean insert(final UserInf info) throws SQLException {
        if (is(info.getUname())) {
            return false;
        }

        final String sql = "INSERT INTO users(name,pwd,mobile) VALUES(?,?,?)";
        final PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, info.getUname());
        pstmt.setString(2, info.getUpwd());
        pstmt.setString(3, info.getUmobile());

        pstmt.execute();
        pstmt.close();
        return true;
    }

    /**
     * 
     * @param name 用户名
     * @return 返回用户名是否存在
     * @throws SQLException
     */
    public static boolean is(final String name) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM users where name = '" + name + "'";
        final Statement stmt = conn.createStatement();
        final ResultSet res = stmt.executeQuery(sql);
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

    /**
     * 
     * @param info Class {@code UserInf}
     * @return 确认{@code info}的密码是否正确
     * @throws SQLException
     */
    public static boolean check(final UserInf info) throws SQLException {
        if (!is(info.getUname()))
            return false;

        final String sql = "SELECT pwd FROM users where name = ?";
        final PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, info.getUname());
        final ResultSet resultSet = pstmt.executeQuery();
        String pwd = null;
        if (resultSet.next()) {
            pwd = resultSet.getString("pwd");
        }
        assert pwd != null;

        resultSet.close();
        pstmt.close();
        // 不要频繁关闭连接

        return pwd.equals(info.getUpwd());

    }

    /**
     * 
     * @return {@link List}<{@linkplain UserInf}> 数据库中的全部信息
     * @throws SQLException
     */
    public static List<UserInf> queryAll() throws SQLException {
        final String sql = "SELECT * FROM users";
        final Statement stmt = conn.createStatement();
        final ResultSet resultSet = stmt.executeQuery(sql);
        final List<UserInf> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new UserInf(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
        }
        resultSet.close();
        stmt.close();
        return list;
    }

    /**
     * 
     * @param uname 删除数据库中的{@code uname}
     * @throws SQLException
     */
    public static void delete(final String uname) throws SQLException {

        final String sql = "DELETE FROM users WHERE name = '" + uname + "'";
        final Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();

    }

    public static List<UserInf> queryByPage(int curPage, int pageSize) throws SQLException {
        final String sql = "SELECT * FROM users limit ?,?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, curPage * pageSize);
        pstmt.setInt(2, pageSize);
        ResultSet rs = pstmt.executeQuery();
        List<UserInf> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new UserInf(rs.getString("name"), rs.getString("pwd"), rs.getString("mobile")));
        }
        rs.close();
        pstmt.close();
        return list;
    }

    public static int queryCount() throws SQLException {
        final String sql = "SELECT COUNT(1) FROM users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = -1;
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        return count;
    }
}