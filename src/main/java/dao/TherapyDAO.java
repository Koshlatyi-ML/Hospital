package dao;

import domain.TherapyDTO;

import java.util.List;

public interface TherapyDAO extends CrudDAO<TherapyDTO> {
    List<TherapyDTO> findCurrentByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findCurrentByPerformerIdAndTypeCount(long id, TherapyDTO.Type type);
    List<TherapyDTO> findCurrentByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findCurrentByPatientIdAndTypeCount(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFinishedByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findFinishedByPerformerIdAndTypeCount(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFinishedByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findFinishedByPatientIdAndTypeCount(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFutureByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findFutureByPerformerIdAndTypeCount(long id, TherapyDTO.Type type);
    List<TherapyDTO> findFutureByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findFutureByPatientIdAndTypeCount(long id, TherapyDTO.Type type);

    List<TherapyDTO> findByPerformerIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findByPerformerIdAndTypeCount(long id, TherapyDTO.Type type);
    List<TherapyDTO> findByPatientIdAndType(long id, TherapyDTO.Type type, int offset, int limit);
    long findByPatientIdAndTypeCount(long id, TherapyDTO.Type type);

    List<TherapyDTO> findFinishedByPatientId(long id, int offset, int limit);
    long findFinishedByPatientIdCount(long id);
    List<TherapyDTO> findNotFinishedByPatientId(long id, int offset, int limit);
    long findNotFinishedByPatientIdCount(long id);
}
