package top.mine.website.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import top.mine.website.entity.Myfile;
import top.mine.website.entity.User;
import top.mine.website.util.BCrypt;

/**
 * UserDAO
 */
public class UserDAO {

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = UserDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(User user) {
        String sql = "INSERT INTO user(name,alias,pwd) VALUES(?,?,?)";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAlias());
            pstmt.setString(3, BCrypt.hashpw(user.getPwd(), BCrypt.gensalt()));
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insert(Collection<User> users) {
        String sql = "INSERT INTO user(name,alias,pwd) VALUES(?,?,?)";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            for (User user : users) {
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getAlias());
                pstmt.setString(3, BCrypt.hashpw(user.getPwd(), BCrypt.gensalt()));
                pstmt.execute();
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String getAlias(String name) {
        String sql = "SELECT alias FROM user WHERE name = ?";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                String a = res.getString(1);
                res.close();
                pstmt.close();
                return a;
            } else {
                res.close();
                pstmt.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean check(User user) {
        String sql = "SELECT pwd FROM user where name = ?";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            ResultSet res = pstmt.executeQuery();
            String pwd = null;
            if (res.next()) {
                pwd = res.getString(1);
            }
            pstmt.close();
            res.close();
            if (pwd == null)
                return false;
            return BCrypt.checkpw(user.getPwd(), pwd);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void aliasChange(User user) throws SQLException {
        String sql = "UPDATE  user SET alias = ? WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.getAlias());
        ps.setString(2, user.getName());
        ps.execute();
        ps.close();
    }

    public static List<User> all() {
        String sql = "SELECT * FROM user";
        LinkedList<User> users = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            users = new LinkedList<>();
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), null));
            }
            rs.close();
            stmt.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String password(String name) throws SQLException {
        String sql = "SELECT pwd FROM user WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        String pwd = null;
        if (rs.next())
            pwd = rs.getString(1);
        rs.close();
        ps.close();
        return pwd;
    }

    public static boolean delete(String name) {
        String sql = "DELETE FROM user WHERE name=?";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.execute();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("删除失败");
            e.printStackTrace();
            return false;
        }

    }

    public static void delete(Collection<String> users) {
        String sql = "DELETE FROM user WHERE name=?";
        try {
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            for (String name : users) {
                pstmt.setString(1, name);
                pstmt.execute();
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param name user name
     * @return a list of file path
     * @throws SQLException
     */
    public static List<Myfile> filesList(String name) throws SQLException {
        int user_id = getUserId(name);
        String sql = "SELECT filename, fileLength FROM myfile WHERE user_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        List<Myfile> list = new LinkedList<>();
        while (rs.next()) {
            list.add(new Myfile(user_id, rs.getString(1), rs.getLong(2)));
        }
        rs.close();
        ps.close();
        return list;
    }

    public static int getUserId(String name) throws SQLException {
        String sql = "SELECT id FROM user WHERE name=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int user_id = -1;
        if (rs.next()) {
            user_id = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return user_id;
    }

    public static int fileCount(User user) throws SQLException {
        int userId = getUserId(user.getName());
        String sql = "SELECT COUNT(*) FROM myfile WHERE user_id=" + userId;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

}