package dao.connection;

import dao.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import util.init.Jndi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;

public class JndiConnectionProvider extends ConnectionProvider {
    @Jndi("java:comp/env/jdbc/postgres")
    private DataSource dataSource;
    private static final Logger LOG = LogManager.getLogger(JndiConnectionProvider.class);

    private JndiConnectionProvider() {
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Could not get connection from data source");
            throw new DaoException(e);
        }
    }
}
