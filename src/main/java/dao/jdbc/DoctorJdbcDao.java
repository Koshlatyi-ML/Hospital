package dao.jdbc;

import dao.DoctorDao;
import dao.jdbc.query.DoctorQueryPreparer;
import dao.jdbc.query.QueryPreparer;
import dao.jdbc.query.StuffQueryPreparer;
import dao.jdbc.retrieve.DoctorEntityRetriever;
import dao.jdbc.supply.DoctorValueSupplier;
import dao.metadata.DoctorTableInfo;
import domain.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorJdbcDao extends StuffJdbcDao<Doctor, StuffQueryPreparer>
        implements DoctorDao {

    private DoctorQueryPreparer doctorQueryPreparer = new DoctorQueryPreparer();
    private PatientJdbcDao patientDao;
    private TherapyJdbcDao therapyDao;

    public DoctorJdbcDao(PatientJdbcDao patientDao, TherapyJdbcDao therapyDao) {
        this.patientDao = patientDao;
        this.therapyDao = therapyDao;
        entityRetriever = new DoctorEntityRetriever();
        valueSupplier = new DoctorValueSupplier();
        queryPreparer = new StuffQueryPreparer();
    }

    @Override
    public Optional<Doctor> find(long id) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        Optional<Doctor> doctorOptional = super.find(id);
        doctorOptional.ifPresent(doctor -> {
            setPatients(connection, doctor);
            setTherapies(connection, doctor);
        });

        releaseThreadLocalConnection();

        return doctorOptional;
    }

    @Override
    public List<Doctor> findAll() {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        List<Doctor> doctorList = super.findAll();
        setPatients(connection, doctorList);
        setTherapies(connection, doctorList);

        releaseThreadLocalConnection();
        return doctorList;
    }

    @Override
    public void create(Doctor entity) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = queryPreparer.prepareInsert(connection);
            valueSupplier.supplyValues(statement, entity);
            statement.execute();
            long aLong = statement.getGeneratedKeys().getLong(1);

            statement = doctorQueryPreparer.prepareInsert(connection);
            statement.setLong(1, aLong);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            connection.setAutoCommit(false);

            statement = queryPreparer.prepareDelete(connection);
            statement.setLong(1, id);
            statement.execute();

            statement = doctorQueryPreparer.prepareDelete(connection);
            statement.setLong(1, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> findByFullName(String name, String surname) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        List<Doctor> doctorList = super.findByFullName(name, surname);
        setPatients(connection, doctorList);
        setTherapies(connection, doctorList);

        releaseThreadLocalConnection();
        return doctorList;
    }

    @Override
    public List<Doctor> findByDepartmentId(long id) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        List<Doctor> doctorList = super.findByDepartmentId(id);
        setPatients(connection, doctorList);
        setTherapies(connection, doctorList);

        releaseThreadLocalConnection();
        return doctorList;
    }

    @Override
    public Optional<Doctor> findByPatientId(long id) {
        Connection connection = getConnection();
        setThreadLocalConnection(connection);

        Optional<Doctor> doctorOptional;
        try (PreparedStatement statement
                = doctorQueryPreparer.prepareFindByPatient(connection)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            doctorOptional = entityRetriever.retrieveEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        doctorOptional.ifPresent(doctor -> {
            setPatients(connection, doctor);
            setTherapies(connection, doctor);
        });

        releaseThreadLocalConnection();
        return doctorOptional;
    }

    private void setPatients(Connection connection, Doctor doctor) {
        long doctorId = doctor.getId();

        patientDao.setThreadLocalConnection(connection);
        doctor.setPatients(patientDao.findByDoctorId(doctorId));
        patientDao.releaseThreadLocalConnection();
    }

    private void setPatients(Connection connection, List<Doctor> doctors) {
        patientDao.setThreadLocalConnection(connection);
        doctors.forEach(doctor -> {
            doctor.setPatients(patientDao.findByDoctorId(doctor.getId()));
        });
        patientDao.releaseThreadLocalConnection();
    }

    private void setTherapies(Connection connection, Doctor doctor) {
        long doctorId = doctor.getId();

        therapyDao.setThreadLocalConnection(connection);
        doctor.setSurgicalOperations(therapyDao.findOperationsByDoctorId(doctorId));
        doctor.setPharmacotherapies(therapyDao.findPharmacotherapiesByDoctorId(doctorId));
        doctor.setPhysiotherapies(therapyDao.findPhysiotherapiesByDoctorId(doctorId));
        therapyDao.releaseThreadLocalConnection();
    }

    private void setTherapies(Connection connection, List<Doctor> doctors) {
        therapyDao.setThreadLocalConnection(connection);
        doctors.forEach(doctor -> {
            long doctorId = doctor.getId();
            doctor.setSurgicalOperations(therapyDao.findOperationsByDoctorId(doctorId));
            doctor.setPharmacotherapies(therapyDao.findPharmacotherapiesByDoctorId(doctorId));
            doctor.setPhysiotherapies(therapyDao.findPhysiotherapiesByDoctorId(doctorId));
        });
        therapyDao.releaseThreadLocalConnection();
    }
}
