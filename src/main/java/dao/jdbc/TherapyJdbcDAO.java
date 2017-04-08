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
    public List<TherapyDTO> findCurrentByDoctorIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindCurrentByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findCurrentByMedicIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindCurrentByMedicIdAndType(connection, id, type);
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
    public List<TherapyDTO> findFinishedByDoctorIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFinishedByMedicIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFinishedByMedicIdAndType(connection, id, type);
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
    public List<TherapyDTO> findFutureByDoctorIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findFutureByMedicIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindFutureByMedicIdAndType(connection, id, type);
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
    public List<TherapyDTO> findByDoctorIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyDTO> findByMedicIdAndType(long id, TherapyDTO.Type type) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByMedicIdAndType(connection, id, type);
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
