package dao.connection;

import dao.connection.jdbc.JdbcConnectionFactory;
import util.load.Implementation;
import util.load.ImplementationLoader;

import java.sql.Connection;

@Implementation(JdbcConnectionFactory.class)
public abstract class ConnectionFactory {

    private static class Holder {
        private static final ConnectionFactory INSTANCE
                = ImplementationLoader.getInstance()
                .loadInstance(ConnectionFactory.class);
    }

    public static ConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public abstract Connection getConnection();
}
