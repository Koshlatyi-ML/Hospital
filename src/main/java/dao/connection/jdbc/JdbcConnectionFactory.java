package dao.connection.jdbc;

import dao.connection.ConnectionFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionFactory extends ConnectionFactory {
    private PGConnectionPoolDataSource dataSource;

    public JdbcConnectionFactory() {
        this.dataSource = new PGConnectionPoolDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("hospital");
        dataSource.setUser("postgres_user");
        dataSource.setPassword("HughL@urie");
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getPooledConnection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
