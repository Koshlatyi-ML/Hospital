package dao;

import domain.Therapy;

import java.time.ZonedDateTime;
import java.util.List;

public interface TherapyDao extends Dao<Therapy> {
    List<Therapy> findByType(Therapy.Type type); //?
    List<Therapy> findByAppointmentDateTime(ZonedDateTime dateTime); //?
    List<Therapy> findByPatientId(long id);
    List<Therapy> findByMedicId(long id);
    List<Therapy> findByDoctorId(long id);
}
