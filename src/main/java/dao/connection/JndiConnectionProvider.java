package dao.connection;

import util.init.Jndi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JndiConnectionProvider extends ConnectionProvider {
    @Jndi("java:comp/env/jdbc/postgres")
    private DataSource dataSource;

    private JndiConnectionProvider() {
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
