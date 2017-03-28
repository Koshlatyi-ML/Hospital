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
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByDepartmentId(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> findByDoctorId(long id) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByDoctorId(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> findByState(Patient.State state) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByState(connection, state);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PersonQueryExecutor<Patient> getQueryExecutor() {
        return queryExecutor;
    }
}
