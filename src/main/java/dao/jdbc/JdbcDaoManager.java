package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.connection.jdbc.ConnectionManager;

public class JdbcDaoManager extends DaoManager {
    private DaoFactory daoFactory;
    private ConnectionManager connectionManager;

    public JdbcDaoManager() {
        daoFactory = DaoFactory.getInstance();
        connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    @Override
    public void beginTransaction() {
        connectionManager.beginTransaction();
    }

    @Override
    public void setSavepoint() {
        connectionManager.setSavepoint();
    }

    @Override
    public void finishTransaction() {
        connectionManager.finishTransaction();
    }
}
