package dao;

import domain.Doctor;
import java.util.Optional;

public interface DoctorDao extends CrudDao<Doctor>,
        DepartmentMemberDao<Doctor> {

    Optional<Doctor> findByPatientId(long id); //?
}
