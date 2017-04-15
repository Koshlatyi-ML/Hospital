package dao.jdbc;

import dao.TherapyDAO;
import dao.exception.DaoException;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.TherapyDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TherapyJdbcDAO extends CrudJdbcDAO<TherapyDTO> implements TherapyDAO {

    private TherapyQueryExecutor queryExecutor;
    private static final Logger LOG = LogManager.getLogger(TherapyJdbcDAO.class);

    TherapyJdbcDAO(TherapyQueryExecutor queryExecutor,
                   ConnectionManager connectionManager) {
        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public List<TherapyDTO> findCurrentByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindCurrentByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindCurrentByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPatientId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPatientId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findNotFinishedByPatientId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindNotFinishedByPatientId(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findNotFinishedByPatientId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    protected QueryExecutor<TherapyDTO> getQueryExecutor() {
        return queryExecutor;
    }

}
