package dao;

import domain.Doctor;
import java.util.Optional;

public interface DoctorDao extends PersonDao<Doctor>,
        DepartmentMemberDao<Doctor> {

    Optional<Doctor> findByPatientId(long id); //?
}
