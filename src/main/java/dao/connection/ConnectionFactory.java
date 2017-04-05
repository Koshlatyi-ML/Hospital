package dao.connection;

import dao.connection.jdbc.JdbcConnectionFactory;
import util.load.Implementation;
import util.load.ImplementationLoader;
import util.load.ImplementationLoaderFactory;

import java.sql.Connection;
import java.util.NavigableSet;
import java.util.TreeSet;

@Implementation(JdbcConnectionFactory.class)
public abstract class ConnectionFactory {

    private static class Holder {
        private static final ConnectionFactory INSTANCE =
                ImplementationLoaderFactory.getInstance()
                        .createImplementationLoader()
                        .loadInstance(ConnectionFactory.class);
    }

    static ConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public abstract Connection getConnection();
}
