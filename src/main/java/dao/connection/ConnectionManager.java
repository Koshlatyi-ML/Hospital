package dao.connection;

import dao.connection.exception.IllegalTransactionStateException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionManager {
    private ConnectionFactory connectionFactory;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<Integer> nestedTransactionsThreadLocal;

    ConnectionManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.connectionThreadLocal = new ThreadLocal<>();
        this.nestedTransactionsThreadLocal = ThreadLocal.withInitial(() -> 0);
    }

    public Connection getConnection() {
        return Optional.ofNullable(connectionThreadLocal.get())
                .orElse(connectionFactory.getConnection());
    }

    private Connection transactionalProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Connection.class, AutoCloseable.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("close") && isTransactional()) {
                        return null;
                    }

                    return method.invoke(connection, args);
                });
    }

    public void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    public void beginTransaction(int isolationLevel) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
        } catch (SQLException e) {
            try {
                connection.close();
                nestedTransactionsThreadLocal.set(0);
            } catch (SQLException onClose) {
                throw new RuntimeException(onClose);
            }
            throw new RuntimeException(e);
        }

        nestedTransactionsThreadLocal.set(nestedTransactionsThreadLocal.get() + 1);

        if (!isTransactional()) {
            connectionThreadLocal.set(transactionalProxyConnection(connection));
        }
    }

    public void finishTransaction() {
        if (!isTransactional()) {
            throw new IllegalTransactionStateException();
        }

        if (nestedTransactionsThreadLocal.get() > 1) {
            nestedTransactionsThreadLocal.set(nestedTransactionsThreadLocal.get() - 1);
            return;
        }

        try (Connection connection = connectionThreadLocal.get()) {
            connectionThreadLocal.set(null);
            nestedTransactionsThreadLocal.set(0);
            try {
                connection.commit();
            } catch (SQLException onCommit) {
                connection.rollback();
            }
        } catch (SQLException onRollbackOrClose) {
            throw new RuntimeException(onRollbackOrClose);
        }
    }

    public void tryRollback() {
        if (isTransactional()) {
            try (Connection connection = connectionThreadLocal.get()) {
                connectionThreadLocal.set(null);
                nestedTransactionsThreadLocal.set(0);
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isTransactional() {
        return connectionThreadLocal.get() != null;
    }
}
