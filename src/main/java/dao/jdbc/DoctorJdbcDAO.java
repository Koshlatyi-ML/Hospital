package dao.jdbc;

import dao.DaoManager;
import dao.DoctorDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.DoctorQueryExecutor;
import domain.dto.DoctorDTO;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorJdbcDAO extends StuffJdbcDAO<DoctorDTO> implements DoctorDAO {
    private DoctorQueryExecutor queryExecutor;

    DoctorJdbcDAO(DoctorQueryExecutor queryExecutor,
                  ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
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
                connectionManager.rollbackTransaction();
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

    private static String getAliased(List<String> strings) {
        return strings.stream()
                .map(s -> s += " AS \"" + s + "\"")
                .collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        DaoManager daoManager = DaoManager.getInstance();
//        DaoFactory daoFactory = daoManager.getDaoFactory();
//        DoctorDAO doctorDao = daoFactory.getDoctorDao();

//        DoctorDTO doc = new DoctorDTO.Builder()
//                .setTitle("qw")
//                .setSurname("sdfc")
//                .setDepartmentId(93)
//                .build();

//        doctorDao.create(doc);

//        Connection connection = ConnectionManager.getInstance().getConnection();
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT " +
//                            getAliased(TableInfoFactory.getInstance().getStuffTableInfo().getNonGeneratingColumns()) +
//                            "," + getAliased(TableInfoFactory.getInstance().getPatientTableInfo().getNonGeneratingColumns())  +
//                            "FROM stuff INNER JOIN  patients ON stuff.id = patients.doctor_id;");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }




//        doctorDao.find(44).ifPresent(System.out::println);
//        doctorDao.find(440).ifPresent(System.out::println);
//        System.out.println();
//        doctorDao.findAll().forEach(System.out::println);
//        System.out.println();
//        doctorDao.findByDepartmentId(930);
//        System.out.println();
//        doctorDao.findByFullName("СаНя").forEach(System.out::println);
//        doctorDao.findByFullName("[SDFSDG").forEach(System.out::println);
//        System.out.println();
//        doctorDao.findByDepartmentId(93).forEach(System.out::println);
//        doctorDao.findByDepartmentId(930).forEach(System.out::println);
//        System.out.println();
//        doctorDao.findByPatientId(4).ifPresent(System.out::println);
//        doctorDao.findByPatientId(40).ifPresent(System.out::println);

//        Doctor doc2 = new Doctor.Builder()
//                .setTitle("lalik")
//                .setSurname("banka")
//                .build();

//        doctorDao.create(doc2);
//        doctorDao.update(doc);

//        doctorDao.update();
//        doctorDao.delete(33);
    }
}
