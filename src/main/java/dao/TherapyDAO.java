package dao;

import domain.Therapy;
import domain.dto.TherapyDTO;

import java.util.List;

public interface TherapyDAO extends CrudDAO<TherapyDTO> {
    List<TherapyDTO> findCurrentByDoctorIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findCurrentByMedicIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findCurrentByPatientIdAndType(long id, Therapy.Type type);

    List<TherapyDTO> findFinishedByDoctorIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findFinishedByMedicIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findFinishedByPatientIdAndType(long id, Therapy.Type type);

    List<TherapyDTO> findFutureByDoctorIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findFutureByMedicIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findFutureByPatientIdAndType(long id, Therapy.Type type);

    List<TherapyDTO> findByDoctorIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findByMedicIdAndType(long id, Therapy.Type type);
    List<TherapyDTO> findByPatientIdAndType(long id, Therapy.Type type);
}
