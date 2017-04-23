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
    public List<TherapyDTO> findCurrentByPerformerIdAndType(long id, TherapyDTO.Type type,
                                                            int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindCurrentByPerformerIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findCurrentByPerformerIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindCurrentByPerformerIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPerformerIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type,
                                                          int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindCurrentByPatientIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findCurrentByPatientIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindCurrentByPatientIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }


    @Override
    public List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type,
                                                             int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFinishedByPerformerIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findFinishedByPerformerIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFinishedByPerformerIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPerformerIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type,
                                                           int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFinishedByPatientIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findFinishedByPatientIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFinishedByPatientIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type,
                                                           int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFutureByPerformerIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findFutureByPerformerIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFutureByPerformerIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPerformerIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type,
                                                         int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFutureByPatientIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findFutureByPatientIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindFutureByPatientIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFutureByPatientIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type,
                                                     int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindByPerformerIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPerformerIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findByPerformerIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindByPerformerIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findCurrentByPerformerIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type,
                                                   int offset, int limit) {

        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindByPatientIdAndType(connection, id, type, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPatientIdAndType", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findByPatientIdAndTypeCount(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor
                    .queryFindByPatientIdAndTypeCount(connection, id, type);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findByPatientIdAndTypeCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPatientId(long id, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPatientId(connection, id, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findFinishedByPatientIdCount(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPatientIdCount(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findFinishedByPatientIdCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public List<TherapyDTO> findNotFinishedByPatientId(long id, int offset, int limit) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindNotFinishedByPatientId(connection, id, offset, limit);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findNotFinishedByPatientId", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    public long findNotFinishedByPatientIdCount(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindNotFinishedByPatientIdCount(connection, id);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Can't query findNotFinishedByPatientIdCount", e);
            connectionManager.tryRollback();
            throw new DaoException(e);
        }
    }

    @Override
    protected QueryExecutor<TherapyDTO> getQueryExecutor() {
        return queryExecutor;
    }

}
