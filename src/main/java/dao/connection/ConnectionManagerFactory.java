package dao.connection;

public class ConnectionManagerFactory {
    private final ConnectionManager connectionManager;

    private ConnectionManagerFactory() {
        connectionManager = new ConnectionManager(ConnectionFactory.getInstance());
    }

    private static class Holder {
        private static final ConnectionManagerFactory INSTANCE =
                new ConnectionManagerFactory();
    }

    public static ConnectionManagerFactory getInstance() {
        return Holder.INSTANCE;
    }

    public ConnectionManager createConnectionManager() {
        return connectionManager;
    }
}
