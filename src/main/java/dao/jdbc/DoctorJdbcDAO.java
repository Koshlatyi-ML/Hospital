package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.DoctorDAO;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Doctor;
import domain.Therapy;
import domain.dto.DoctorDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorJdbcDAO extends StuffJdbcDAO<DoctorDTO> implements DoctorDAO {
    private DoctorQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DoctorJdbcDAO(QueryExecutorFactory queryExecutorFactory,
                  JdbcDaoFactory jdbcDaoFactory,
                  ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutorFactory.getDoctorQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<DoctorDTO> findByPatientId(long id) {
        Optional<DoctorDTO> doctorOptional;

        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                doctorOptional =
                        queryExecutor.queryFindByPatientId(connection, id);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                doctorOptional =
                        queryExecutor.queryFindByPatientId(localConnection, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return doctorOptional;
    }

    @Override
    protected DoctorQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    public static void main(String[] args) {
        DaoManager daoManager = DaoManager.getInstance();
        DaoFactory daoFactory = daoManager.getDaoFactory();
        DoctorDAO doctorDao = daoFactory.getDoctorDao();

        DoctorDTO doc = new DoctorDTO.Builder()
                .setId(42)
                .setName("xXx")
                .setSurname("yYy")
                .setDepartmentId(93)
                .build();
        doctorDao.delete(42);


//        Doctor doc2 = new Doctor.Builder()
//                .setName("lalik")
//                .setSurname("banka")
//                .build();

//        doctorDao.create(doc2);
//        doctorDao.update(doc);

//        doctorDao.update();
//        doctorDao.delete(33);
    }
}
