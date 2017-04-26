package dao.jdbc;

import dao.connection.ConnectionProvider;

import java.sql.Connection;

public class TestJdbcConnectionManager extends ConnectionManager  {

    private ConnectionProvider proxyProvider;

    public TestJdbcConnectionManager(ConnectionProvider connectionProvider) {
        super(connectionProvider);
        proxyProvider = connectionProvider;
    }

    public Connection getConnection() {
        return proxyProvider.getConnection();
    }

    void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    void beginTransaction(int isolationLevel) {
    }

    void finishTransaction() {
    }

    void tryRollback() {
    }
}
