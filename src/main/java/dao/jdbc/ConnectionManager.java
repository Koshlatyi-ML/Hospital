package dao.jdbc;

import dao.connection.ConnectionProvider;
import dao.exception.DaoException;
import dao.exception.IllegalTransactionStateException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

public class ConnectionManager {

    private ConnectionProvider connectionProvider;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<LinkedList<Integer>> isolationLevelStackThreadLocal;
    private static final Logger LOG = LogManager.getLogger(ConnectionManager.class);

    ConnectionManager(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.connectionThreadLocal = new ThreadLocal<>();
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
                            && ((Integer) args[0]).compareTo(getIsolationLevel()) < 0) {

                        return null;
                    }

                    return method.invoke(connection, args);
                });
    }

    void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    void beginTransaction(int isolationLevel) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't open transaction", e);
            tryRollback();
            throw new DaoException(e);
        }

        isolationLevelStackThreadLocal.get().push(isolationLevel);

        if (!isTransactional()) {
            connectionThreadLocal.set(transactionalProxyConnection(connection));
        }
    }

    void finishTransaction() {
        if (!isTransactional()) {
            throw new IllegalTransactionStateException();
        }

        if (isNestedTransactional()) {
            resetTransactionIsolation();
            return;
        }

        try (Connection connection = connectionThreadLocal.get()) {
            connectionThreadLocal.set(null);
            isolationLevelStackThreadLocal.get().clear();
            connection.commit();
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't commit the transaction", e);
            tryRollback();
            throw new DaoException(e);
        }
    }

    void tryRollback() {
        if (isTransactional()) {
            try (Connection connection = connectionThreadLocal.get()) {
                connectionThreadLocal.set(null);
                isolationLevelStackThreadLocal.get().clear();
                connection.rollback();
            } catch (SQLException e) {
                LOG.log(Level.ERROR, "Can't rollback the transaction", e);
                throw new DaoException(e);
            }
        }
    }

    private void resetTransactionIsolation() {
        isolationLevelStackThreadLocal.get().pop();
        Integer outerIsolationLevel = isolationLevelStackThreadLocal.get().peek();
        try {
            connectionThreadLocal.get().setTransactionIsolation(outerIsolationLevel);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't reset the transaction isolation level", e);
            tryRollback();
            throw new DaoException(e);
        }
    }

    private boolean isTransactional() {
        return connectionThreadLocal.get() != null;
    }

    private boolean isNestedTransactional() {
        return isolationLevelStackThreadLocal.get().size() > 1;
    }

    private int getIsolationLevel() {
        return isolationLevelStackThreadLocal.get().peek();
    }
}
