package dao;

import domain.dto.TherapyDTO;

import java.util.List;

public interface TherapyDAO extends CrudDAO<TherapyDTO> {
    List<TherapyDTO> findCurrentByPerformerIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type);

    List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type);
    List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type);
}
