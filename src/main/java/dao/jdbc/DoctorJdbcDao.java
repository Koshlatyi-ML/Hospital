package dao.jdbc;

import dao.DoctorDao;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.metadata.TableInfoFactory;
import domain.Doctor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DoctorJdbcDao extends StuffJdbcDao<Doctor> implements DoctorDao {

    private DoctorQueryExecutor queryExecutor;
    private PatientJdbcDao patientDao;
    private TherapyJdbcDao therapyDao;

    public DoctorJdbcDao(PatientJdbcDao patientDao, TherapyJdbcDao therapyDao) {
        this.queryExecutor = new DoctorQueryExecutor(TableInfoFactory.getInstance());
        this.patientDao = patientDao;
        this.therapyDao = therapyDao;
    }

    @Override
    public Optional<Doctor> find(long id) {
        Optional<Doctor> doctorOptional;

        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            doctorOptional = super.find(id);
            doctorOptional.ifPresent(doctor -> {
                setPatients(connection, doctor);
                setTherapies(connection, doctor);
            });
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return doctorOptional;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctorList;

        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            doctorList = super.findAll();
            setPatients(connection, doctorList);
            setTherapies(connection, doctorList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }
        return doctorList;
    }

    @Override
    public List<Doctor> findByFullName(String name, String surname) {
        List<Doctor> doctorList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            doctorList = super.findByFullName(name, surname);
            setPatients(connection, doctorList);
            setTherapies(connection, doctorList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return doctorList;
    }

    @Override
    public List<Doctor> findByDepartmentId(long id) {
        List<Doctor> doctorList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            doctorList = super.findByDepartmentId(id);
            setPatients(connection, doctorList);
            setTherapies(connection, doctorList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return doctorList;
    }


    @Override
    public Optional<Doctor> findByPatientId(long id) {
        Optional<Doctor> doctorOptional;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            doctorOptional = queryExecutor.queryFindByPatientId(connection, id);
            doctorOptional.ifPresent(doctor -> {
                setPatients(connection, doctor);
                setTherapies(connection, doctor);
            });
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }
        return doctorOptional;
    }


    @Override
    protected DoctorQueryExecutor getQueryExecutor() {
        return queryExecutor;
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
