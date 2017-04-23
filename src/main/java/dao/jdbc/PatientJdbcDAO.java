package dao.jdbc;

import dao.PatientDAO;
import dao.exception.DaoException;
import dao.jdbc.query.PatientQueryExecutor;
import domain.PatientDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PatientJdbcDAO extends CrudJdbcDAO<PatientDTO> implements PatientDAO {

    private PatientQueryExecutor queryExecutor;
    private static final Logger LOG = LogManager.getLogger(PatientJdbcDAO.class);

    PatientJdbcDAO(PatientQueryExecutor queryExecutor,
                   ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public List<PatientDTO> findByFullName(String fullName, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByFullName(connection, fullName, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByFullName", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<PatientDTO> findByDepartmentId(long id, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDepartmentId(connection, id, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<PatientDTO> findByLoginAndPassword(String login, String password) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByLoginAndPassword(connection, login, password);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByLoginAndPassword", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<PatientDTO> findByCredentialsId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByCredentials", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<PatientDTO> findByDoctorId(long id, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDoctorId(connection, id, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByDoctorId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<PatientDTO> findByState(PatientDTO.State state, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByState(connection, state, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByState", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<PatientDTO> findByDoctorIdAndState(long doctorId, PatientDTO.State state,
                                                   int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindByDoctorIdAndState(connection, doctorId, state, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByDoctorIdAndState", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    protected PatientQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }
}
