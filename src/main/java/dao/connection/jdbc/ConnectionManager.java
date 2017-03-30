package dao.connection.jdbc;

import dao.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionManager {
    private ConnectionFactory connectionFactory;
    private ThreadLocal<Connection> connectionThreadLocal;

    private static final ConnectionManager INSTANCE = new ConnectionManager();

    private ConnectionManager() {
        connectionFactory = ConnectionFactory.getInstance();
        connectionThreadLocal = new ThreadLocal<>();
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
                connection.rollback();
            }
        } catch (SQLException onRollbackOrClose) {
            throw new RuntimeException(onRollbackOrClose);
        }
    }

    public void rollbackAndClose(Connection connection) {
        connectionThreadLocal.set(null);
        try (Connection localConnection = connection) {
            localConnection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    private void commit(Connection connection) {
//        try (Connection localConnection = connection) {
//            localConnection.commit();
//            connectionThreadLocal.set(null);
//        } catch (SQLException e) {
//            rollbackAndClose(connection);
//            throw new RuntimeException(e);
//        }
//    }

    public boolean isTransactional() {
        return Optional.ofNullable(connectionThreadLocal.get()).isPresent();
    }
}
