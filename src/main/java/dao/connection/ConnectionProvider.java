package dao.connection;

import util.load.Implementation;
import util.load.ImplementationLoaderFactory;

import java.sql.Connection;

@Implementation(JndiConnectionProvider.class)
public abstract class ConnectionProvider {

    private static class SingletonHolder {
        private SingletonHolder() {}
        private static final ConnectionProvider INSTANCE =
                ImplementationLoaderFactory.getInstance()
                        .createImplementationLoader()
                        .loadInstance(ConnectionProvider.class);
    }

    public static ConnectionProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public abstract Connection getConnection();
}
