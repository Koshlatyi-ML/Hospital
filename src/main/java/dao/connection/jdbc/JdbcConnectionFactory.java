package dao.connection.jdbc;

import dao.connection.ConnectionFactory;
import util.load.init.Jndi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionFactory extends ConnectionFactory {
    @Jndi("java:comp/env/jdbc/postgres")
    private DataSource dataSource;

    private JdbcConnectionFactory() {    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
