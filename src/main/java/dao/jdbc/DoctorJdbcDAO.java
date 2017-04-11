package dao.jdbc;

import dao.DoctorDAO;
import dao.exception.DaoException;
import dao.jdbc.query.DoctorQueryExecutor;
import domain.DoctorDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class DoctorJdbcDAO extends StuffJdbcDAO<DoctorDTO> implements DoctorDAO {

    private DoctorQueryExecutor queryExecutor;
    private static final Logger LOG = LogManager.getLogger(DoctorJdbcDAO.class);

    DoctorJdbcDAO(DoctorQueryExecutor queryExecutor,
                  ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<DoctorDTO> findByPatientId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByPatientId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPatientId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<DoctorDTO> findByCredentialsId(long id) {
        return JdbcDaoCommons.findByCredentialsId(connectionManager, queryExecutor, id);
    }

    @Override
    protected DoctorQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }
}
