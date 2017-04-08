package dao.jdbc;

import dao.DoctorDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.DoctorQueryExecutor;
import domain.dto.DoctorDTO;

import java.sql.*;
import java.util.Optional;

public class DoctorJdbcDAO extends StuffJdbcDAO<DoctorDTO> implements DoctorDAO {
    private DoctorQueryExecutor queryExecutor;

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
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<DoctorDTO> findByCredentialsId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DoctorQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }
}
