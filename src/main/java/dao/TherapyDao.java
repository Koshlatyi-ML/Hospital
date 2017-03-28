package dao;

import domain.Therapy;

import java.time.ZonedDateTime;
import java.util.List;

public interface TherapyDao extends CrudDao<Therapy> {
    List<Therapy> findCurrentByDoctorIdAndType(long id, Therapy.Type type);
    List<Therapy> findCurrentByMedicIdAndType(long id, Therapy.Type type);
    List<Therapy> findCurrentByPatientIdAndType(long id, Therapy.Type type);

    List<Therapy> findFinishedByDoctorIdAndType(long id, Therapy.Type type);
    List<Therapy> findFinishedByMedicIdAndType(long id, Therapy.Type type);
    List<Therapy> findFinishedByPatientIdAndType(long id, Therapy.Type type);

    List<Therapy> findFutureByDoctorIdAndType(long id, Therapy.Type type);
    List<Therapy> findFutureByMedicIdAndType(long id, Therapy.Type type);
    List<Therapy> findFutureByPatientIdAndType(long id, Therapy.Type type);

    List<Therapy> findByDoctorIdAndType(long id, Therapy.Type type);
    List<Therapy> findByMedicIdAndType(long id, Therapy.Type type);
    List<Therapy> findByPatientIdAndType(long id, Therapy.Type type);
}
