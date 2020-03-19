package mine.learn.javawebajax.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * AdminDAO
 */
public class AdminDAO {

    private static Connection conn;
    static {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = AdminDAO.class.getClassLoader().getResourceAsStream("jdbc.properties");
            assert inputStream != null;
            properties.load(inputStream);
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}