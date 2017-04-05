package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.PatientDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.PersonQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PatientJdbcDAO extends PersonJdbcDAO<PatientDTO> implements PatientDAO {
    private PatientQueryExecutor queryExecutor;

    PatientJdbcDAO(QueryExecutorFactory queryExecutorFactory,
                   ConnectionManager connectionManager) {
        this.queryExecutor = queryExecutorFactory.getPatientQueryExecutor();
        this.connectionManager = connectionManager;
    }

    @Override
    public List<PatientDTO> findByDepartmentId(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByDepartmentId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<PatientDTO> findByDoctorId(long id) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByDoctorId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<PatientDTO> findByState(Patient.State state) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor.queryFindByState(connection, state);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    protected PersonQueryExecutor<PatientDTO> getQueryExecutor() {
        return queryExecutor;
    }

    public static void main(String[] args) {
        DaoManager daoManager = DaoManager.getInstance();
//        DaoFactory daoFactory = daoManager.getDaoFactory();
//        PatientDAO patientDAO = daoFactory.getPatientDao();

        PatientDTO dto = new PatientDTO.Builder()
                .setName("Kolya")
                .setSurname("Koshlatyi")
                .setDoctorId(44)
                .setCompliants("Sore throat")
                .setDiagnosis("Volchanka")
//                .setState("ADDMITTED")
                .build();
//        patientDAO.create(dto);

//        dto.setDiagnosis("Brain cancer");
//        patientDAO.update(dto);
//
//        patientDAO.delete(dto);
    }
}
