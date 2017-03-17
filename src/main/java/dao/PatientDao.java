package dao;

import domain.Patient;

import java.util.List;

public interface PatientDao extends Dao<Patient>,
        DepartmentMemberDao<Patient> {
    List<Patient> findByDoctorId(long id);
    List<Patient> findByState(Patient.State state); //?
}
