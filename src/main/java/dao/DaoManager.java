package dao;

import dao.jdbc.JdbcDaoManager;
import util.load.Implementation;
import util.load.ImplementationLoader;

import java.sql.Connection;
import java.sql.Statement;

@Implementation(JdbcDaoManager.class)
public abstract class DaoManager {
    private static final DaoManager INSTANCE =
            ImplementationLoader.getInstance().loadInstance(DaoManager.class);

    public static DaoManager getInstance() {
        return INSTANCE;
    }

    public abstract DaoFactory getDaoFactory();
    public abstract void beginTransaction();
    public abstract void beginTransaction(int isolationLevel);
    public abstract void setSavepoint();
    public abstract void finishTransaction();
}
