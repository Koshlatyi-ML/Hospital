package dao;

import dao.jdbc.JdbcDaoManager;
import util.load.Implementation;
import util.load.ImplementationLoader;
import util.load.ImplementationLoaderFactory;

import java.sql.Connection;
import java.sql.Statement;

@Implementation(JdbcDaoManager.class)
public abstract class DaoManager implements DaoFactory {
    private static final DaoManager INSTANCE = ImplementationLoaderFactory.getInstance()
            .createImplementationLoader().loadInstance(DaoManager.class);

    public static DaoManager getInstance() {
        return INSTANCE;
    }

    public abstract void beginTransaction();
    public abstract void beginTransaction(int isolationLevel);
    public abstract void finishTransaction();
}
