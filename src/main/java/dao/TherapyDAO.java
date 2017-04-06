package dao;

import domain.Therapy;
import domain.dto.TherapyDTO;

import java.util.List;

public interface TherapyDAO extends CrudDAO<TherapyDTO> {
    List<TherapyDTO> findCurrentByDoctorIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findCurrentByMedicIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFinishedByDoctorIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFinishedByMedicIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFutureByDoctorIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFutureByMedicIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findByDoctorIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findByMedicIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type);
}
