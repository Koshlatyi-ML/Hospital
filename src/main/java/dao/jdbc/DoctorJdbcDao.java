package dao.jdbc;

import dao.DoctorDao;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Doctor;
import domain.Patient;
import domain.Therapy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorJdbcDao extends StuffJdbcDao<Doctor> implements DoctorDao {
    private DoctorQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    DoctorJdbcDao(QueryExecutorFactory queryExecutorFactory,
                  JdbcDaoFactory jdbcDaoFactory) {

        this.queryExecutor = queryExecutorFactory.getDoctorQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    @Override
    public Optional<Doctor> find(long id) {
        try (Connection connection = getConnection()) {
            Optional<Doctor> doctorOptional =
                    queryExecutor.queryFindById(connection, id);
            doctorOptional.ifPresent(doctor -> {
                setPatients(doctor);
                setTherapies(doctor);
            });
            return doctorOptional;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> findAll() {
        try (Connection connection = getConnection()) {
            List<Doctor> doctorList = queryExecutor.queryFindAll(connection);
            setPatients(doctorList);
            setTherapies(doctorList);
            return doctorList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> findByFullName(String name, String surname) {
        try (Connection connection = getConnection()) {
            List<Doctor> doctorList =
                    queryExecutor.queryFindByFullName(connection, name, surname);
            setPatients(doctorList);
            setTherapies(doctorList);
            return doctorList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Doctor> findByDepartmentId(long id) {
        try (Connection connection = getConnection()) {
            List<Doctor> doctorList =
                    queryExecutor.queryFindByDepartmentId(connection, id);
            setPatients(doctorList);
            setTherapies(doctorList);
            return doctorList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Doctor> findByPatientId(long id) {
        try (Connection connection = getConnection()) {
            Optional<Doctor> doctorOptional =
                    queryExecutor.queryFindByPatientId(connection, id);
            doctorOptional.ifPresent(doctor -> {
                setPatients(doctor);
                setTherapies(doctor);
            });
            return doctorOptional;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
