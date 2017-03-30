package dao.jdbc;

import dao.PatientDao;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.PersonQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.metadata.TableInfoFactory;
import dao.metadata.annotation.mapping.Entity;
import domain.Patient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Entity(Patient.class)
public class PatientJdbcDao extends PersonJdbcDao<Patient> implements PatientDao {
    private PatientQueryExecutor queryExecutor;

    PatientJdbcDao(QueryExecutorFactory queryExecutorFactory) {
        this.queryExecutor = queryExecutorFactory.getPatientQueryExecutor();
    }

    @Override
    public List<Patient> findByDepartmentId(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByDepartmentId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor.queryFindByDepartmentId(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Patient> findByDoctorId(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByDoctorId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor.queryFindByDoctorId(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Patient> findByState(Patient.State state) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByState(connection, state);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor.queryFindByState(localConnection, state);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected PersonQueryExecutor<Patient> getQueryExecutor() {
        return queryExecutor;
    }
}
