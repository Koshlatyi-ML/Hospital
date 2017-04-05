package dao.connection;

import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class TestingConnectionFactory extends ConnectionFactory {
    private Properties connectionProperies;
    private final String URL_KEY = "url";
    private final String USER_KEY = "user";
    private final String PASSWORD_KEY = "password";

    private TestingConnectionFactory() {
        String CONNECTION_PROPERTIES_PATH = "dao/connection.properties";
        connectionProperies = PropertyLoader.getInstance()
                .getProperties(CONNECTION_PROPERTIES_PATH);
    }

    private static class Holder {
        private static final TestingConnectionFactory INSTANCE = new TestingConnectionFactory();
    }

    public static TestingConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    connectionProperies.getProperty(URL_KEY),
                    connectionProperies.getProperty(USER_KEY),
                    connectionProperies.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
