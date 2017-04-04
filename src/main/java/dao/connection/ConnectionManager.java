package dao.connection;

import dao.connection.exception.IllegalTransactionStateException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionManager {
    private ConnectionFactory connectionFactory;
    private ThreadLocal<Connection> connectionThreadLocal;
    private boolean isTransactional;

    ConnectionManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.connectionThreadLocal = new ThreadLocal<>();
    }

    public Connection getConnection() {
        return Optional.ofNullable(connectionThreadLocal.get())
                .orElse(connectionFactory.getConnection());
    }

    public void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    public void beginTransaction(int isolationLevel) {
        if (isTransactional) {
            throw new IllegalTransactionStateException();
        }

        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException onClose) {
                throw new RuntimeException(onClose);
            }
            throw new RuntimeException(e);
        }
        connectionThreadLocal.set(connection);
        isTransactional = true;
    }

    public void finishTransaction() {
        if (!isTransactional) {
            throw new IllegalTransactionStateException();
        }

        isTransactional = false;
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

    public void rollbackTransaction() {
        if (!isTransactional) {
            throw new IllegalTransactionStateException();
        }

        Connection connection = connectionThreadLocal.get();
        connectionThreadLocal.set(null);

        isTransactional = false;
        try (Connection localConnection = connection) {
            localConnection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTransactional() {
        return isTransactional;
    }
}
