package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.connection.ConnectionManager;

import java.sql.Connection;

public class JdbcDaoManager extends DaoManager {
    private DaoFactory daoFactory;
    private ConnectionManager connectionManager;

    public JdbcDaoManager() {
        daoFactory = DaoFactory.getInstance();
//        connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    @Override
    public void beginTransaction() {
        beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    @Override
    public void beginTransaction(int isolationLevel) {
        connectionManager.beginTransaction(isolationLevel);
    }

    @Override
    public void finishTransaction() {
        connectionManager.finishTransaction();
    }
}
