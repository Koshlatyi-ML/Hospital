package dao.connection;

import util.load.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class TestingConnectionFactory extends ConnectionFactory {
    private Map<String, String> connectionProperies;
    private final String URL_KEY = "url";
    private final String USER_KEY = "user";
    private final String PASSWORD_KEY = "password";

    private TestingConnectionFactory() {
        String CONNECTION_PROPERTIES_PATH = "dao/connection.properties";
        connectionProperies = PropertyLoader.getInstance().getProperties(
                CONNECTION_PROPERTIES_PATH, Arrays.asList(URL_KEY, USER_KEY, PASSWORD_KEY));
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
                    connectionProperies.get(URL_KEY),
                    connectionProperies.get(USER_KEY),
                    connectionProperies.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
