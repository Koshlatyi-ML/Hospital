package dao;

import domain.TherapyDTO;

import java.util.List;

public interface TherapyDAO extends CrudDAO<TherapyDTO> {
    List<TherapyDTO> findCurrentByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);

    List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);

    List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);

    List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);

    List<TherapyDTO> findFinishedByPatientId(long id, int offset, int limit);
    List<TherapyDTO> findNotFinishedByPatientId(long id, int offset, int limit);
}
