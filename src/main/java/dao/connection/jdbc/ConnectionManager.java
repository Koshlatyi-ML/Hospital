package dao.connection.jdbc;

import dao.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Optional;

public class ConnectionManager {
    private ConnectionFactory connectionFactory;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<Savepoint> savepointThreadLocal;

    private static final ConnectionManager INSTANCE = new ConnectionManager();

    private ConnectionManager() {
        connectionFactory = ConnectionFactory.getInstance();
        connectionThreadLocal = new ThreadLocal<>();
        savepointThreadLocal = new ThreadLocal<>();
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return Optional.ofNullable(connectionThreadLocal.get())
                .orElse(connectionFactory.getConnection());
    }

    public void beginTransaction() {
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            savepointThreadLocal.set(connection.setSavepoint());
        } catch (SQLException onSetAutocommitFalse) {
            try {
                connection.close();
            } catch (SQLException onClose) {
                throw new RuntimeException(onClose);
            }
            throw new RuntimeException(onSetAutocommitFalse);
        }
        connectionThreadLocal.set(connection);
    }

    public void finishTransaction() {
        try (Connection connection = connectionThreadLocal.get()) {
            connectionThreadLocal.set(null);
            try {
                connection.commit();
            } catch (SQLException onCommit) {
                connection.rollback(savepointThreadLocal.get());
            }
        } catch (SQLException onRollbackOrClose) {
            throw new RuntimeException(onRollbackOrClose);
        }
    }

    public void setSavepoint() {
        Connection connection = connectionFactory.getConnection();
        try {
            savepointThreadLocal.set(connection.setSavepoint());
        } catch (SQLException e) {
            rollbackAndClose(connection);
            throw new RuntimeException(e);
        }
    }

    public void rollbackAndClose(Connection connection) {
        connectionThreadLocal.set(null);
        try (Connection localConnection = connection) {
            localConnection.rollback(savepointThreadLocal.get());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTransactional() {
        return Optional.ofNullable(connectionThreadLocal.get()).isPresent();
    }
}
