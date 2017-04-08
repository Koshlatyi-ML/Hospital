package dao.jdbc;

import dao.DaoManager;
import dao.MedicDAO;
import dao.PatientDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.PersonQueryExecutor;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PatientJdbcDAO extends PersonJdbcDAO<PatientDTO> implements PatientDAO {
    private PatientQueryExecutor queryExecutor;

    PatientJdbcDAO(PatientQueryExecutor queryExecutor,
                   ConnectionManager connectionManager) {
        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    public List<PatientDTO> findByDepartmentId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PatientDTO> findByDoctorId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByDoctorId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PatientDTO> findByState(Patient.State state) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByState(connection, state);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PatientDTO> findByCredentialsId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PersonQueryExecutor<PatientDTO> getQueryExecutor() {
        return queryExecutor;
    }
}
