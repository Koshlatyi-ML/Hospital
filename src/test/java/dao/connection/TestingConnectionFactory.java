package dao.connection;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGPooledConnection;
import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class TestingConnectionFactory extends ConnectionFactory {
    private PGConnectionPoolDataSource dataSource;
    private Properties connectionProperies;
    private final String URL_KEY = "url";
    private final String USER_KEY = "user";
    private final String PASSWORD_KEY = "password";

    private TestingConnectionFactory() {
        String CONNECTION_PROPERTIES_PATH = "dao/connection.properties";
        connectionProperies = PropertyLoader.getInstance()
                .getProperties(CONNECTION_PROPERTIES_PATH);
        dataSource = new PGConnectionPoolDataSource();
        dataSource.setUrl(connectionProperies.getProperty(URL_KEY));
        dataSource.setUser(connectionProperies.getProperty(USER_KEY));
        dataSource.setPassword(connectionProperies.getProperty(PASSWORD_KEY));
    }

    private static class Holder {
        private static final TestingConnectionFactory INSTANCE = new TestingConnectionFactory();
    }

    public static TestingConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
