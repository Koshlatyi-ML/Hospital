package dao.connection;

import org.postgresql.ds.PGConnectionPoolDataSource;
import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnectionProvider {
    private PGConnectionPoolDataSource dataSource;
    private Properties connectionProperies;
    private final String URL_KEY = "url";
    private final String USER_KEY = "user";
    private final String PASSWORD_KEY = "password";

    private static final TestConnectionProvider INSTANCE = new TestConnectionProvider();

    private TestConnectionProvider() {
        String CONNECTION_PROPERTIES_PATH = "dao/connection.properties";
        connectionProperies = PropertyLoader.getProperties(CONNECTION_PROPERTIES_PATH);
        dataSource = new PGConnectionPoolDataSource();
        dataSource.setUrl(connectionProperies.getProperty(URL_KEY));
        dataSource.setUser(connectionProperies.getProperty(USER_KEY));
        dataSource.setPassword(connectionProperies.getProperty(PASSWORD_KEY));
    }

    public static TestConnectionProvider getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
