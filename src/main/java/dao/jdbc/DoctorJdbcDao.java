package dao.jdbc;

import dao.DoctorDao;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Doctor;
import domain.Therapy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorJdbcDao extends StuffJdbcDao<Doctor> implements DoctorDao {
    private DoctorQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DoctorJdbcDao(QueryExecutorFactory queryExecutorFactory,
                  JdbcDaoFactory jdbcDaoFactory,
                  ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutorFactory.getDoctorQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<Doctor> find(long id) {
        Optional<Doctor> doctorOptional = super.find(id);
        doctorOptional.ifPresent(doctor -> {
            setPatients(doctor);
            setTherapies(doctor);
        });

        return doctorOptional;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctorList = super.findAll();
        setPatients(doctorList);
        setTherapies(doctorList);
        return doctorList;
    }

    @Override
    public List<Doctor> findByFullName(String name, String surname) {
        List<Doctor> doctorList = super.findByFullName(name, surname);
        setPatients(doctorList);
        setTherapies(doctorList);
        return doctorList;
    }

    @Override
    public List<Doctor> findByDepartmentId(long id) {
        List<Doctor> doctorList = super.findByDepartmentId(id);
        setPatients(doctorList);
        setTherapies(doctorList);
        return doctorList;
    }

    @Override
    public Optional<Doctor> findByPatientId(long id) {
        Optional<Doctor> doctorOptional;

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

        doctorOptional.ifPresent(doctor -> {
            setPatients(doctor);
            setTherapies(doctor);
        });
        return doctorOptional;
    }

    @Override
    protected DoctorQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    private void setPatients(Doctor doctor) {
        setPatients(jdbcDaoFactory.getPatientDao(), doctor);
    }

    private void setPatients(List<Doctor> doctors) {
        PatientJdbcDao patientJdbcDao = jdbcDaoFactory.getPatientDao();
        doctors.forEach(doctor -> setPatients(patientJdbcDao, doctor));
    }

    private void setPatients(PatientJdbcDao patientJdbcDao, Doctor doctor) {
        long doctorId = doctor.getId();
        doctor.setPatients(patientJdbcDao.findByDoctorId(doctorId));
    }

    private void setTherapies(Doctor doctor) {
        setTherapies(jdbcDaoFactory.getTherapyDao(), doctor);
    }

    private void setTherapies(List<Doctor> doctors) {
        TherapyJdbcDao therapyJdbcDao = jdbcDaoFactory.getTherapyDao();
        doctors.forEach(doctor -> setTherapies(therapyJdbcDao, doctor));
    }

    private void setTherapies(TherapyJdbcDao therapyJdbcDao, Doctor doctor) {
        long doctorId = doctor.getId();
        doctor.setSurgicalOperations(therapyJdbcDao.findByDoctorIdAndType(doctorId,
                Therapy.Type.SURGERY_OPERATION));
        doctor.setPharmacotherapies(therapyJdbcDao.findByDoctorIdAndType(doctorId,
                Therapy.Type.PHARMACOTHERAPY));
        doctor.setPhysiotherapies(therapyJdbcDao.findByDoctorIdAndType(doctorId,
                Therapy.Type.PHYSIOTHERAPY));
    }
}
