package dao.jdbc;

import dao.connection.ConnectionProvider;
import dao.exception.IllegalTransactionStateException;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

public class ConnectionManager {

    private ConnectionProvider connectionProvider;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<Integer> nestedTransactionsThreadLocal;
    private ThreadLocal<LinkedList<Integer>> isolationLevelStackThreadLocal;

    ConnectionManager(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.connectionThreadLocal = new ThreadLocal<>();
        this.nestedTransactionsThreadLocal = ThreadLocal.withInitial(() -> 0);
        this.isolationLevelStackThreadLocal = ThreadLocal.withInitial(LinkedList::new);
    }

    public Connection getConnection() {
        return Optional.ofNullable(connectionThreadLocal.get())
                .orElse(connectionProvider.getConnection());
    }

    private Connection transactionalProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Connection.class, AutoCloseable.class},
                (Object proxy, Method method, Object[] args) -> {
                    if ("close".equals(method.getName()) && isTransactional()) {
                        return null;
                    }

                    if ("setAutoCommit".equals(method.getName())) {
                        return null;
                    }

                    if ("setTransactionIsolation".equals(method.getName())
                            && ((Integer) args[0]).compareTo(isolationLevelStackThreadLocal.get().peek()) < 0) {

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

        isolationLevelStackThreadLocal.get().push(isolationLevel);
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
            isolationLevelStackThreadLocal.get().pop();
            Integer outerIsolationLevel = isolationLevelStackThreadLocal.get().peek();
            try {
                connectionThreadLocal.get().setTransactionIsolation(outerIsolationLevel);
            } catch (SQLException e) {
                tryRollback();
                throw new RuntimeException(e);
            }
            nestedTransactionsThreadLocal.set(nestedTransactionsThreadLocal.get() - 1);
            return;
        }

        try (Connection connection = connectionThreadLocal.get()) {
            connectionThreadLocal.set(null);
            isolationLevelStackThreadLocal.get().clear();
            nestedTransactionsThreadLocal.set(0);
            tryCommit(connection);
        } catch (SQLException onRollbackOrClose) {
            throw new RuntimeException(onRollbackOrClose);
        }
    }

    public void tryRollback() {
        if (isTransactional()) {
            try (Connection connection = connectionThreadLocal.get()) {
                connectionThreadLocal.set(null);
                isolationLevelStackThreadLocal.get().clear();
                nestedTransactionsThreadLocal.set(0);
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void tryCommit(Connection connection) throws SQLException {
        try {
            connection.commit();
        } catch (SQLException onCommit) {
            connection.rollback();
            throw new RuntimeException(onCommit);
        }
    }

    private boolean isTransactional() {
        return connectionThreadLocal.get() != null;
    }
}
