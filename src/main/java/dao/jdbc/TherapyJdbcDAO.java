package dao.jdbc;

import dao.TherapyDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.dto.TherapyDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TherapyJdbcDAO extends CrudJdbcDAO<TherapyDTO> implements TherapyDAO {
    private TherapyQueryExecutor queryExecutor;

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
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindCurrentByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByPerformerIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected QueryExecutor<TherapyDTO> getQueryExecutor() {
        return queryExecutor;
    }

}
