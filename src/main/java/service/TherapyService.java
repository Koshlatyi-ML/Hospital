package service;

import dao.CrudDAO;
import dao.DaoManager;
import dao.PatientDAO;
import dao.TherapyDAO;
import domain.PatientDTO;
import domain.TherapyDTO;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TherapyService extends AbstractCrudService<TherapyDTO> {

    private DaoManager daoManager;

    TherapyService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public List<TherapyDTO> getFinishedByPatientId(long patientId, int offset, int limit) {
        return daoManager.getTherapyDao().findFinishedByPatientId(patientId, offset, limit);
    }

    public List<TherapyDTO> getNotFinishedByPatientId(long patientId, int offset, int limit) {
        return daoManager.getTherapyDao().findNotFinishedByPatientId(patientId, offset, limit);
    }

    public List<TherapyDTO> getFinishedSurgeryOperationsByPerformerId(long id, int offset,
                                                                      int limit) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(
                id, TherapyDTO.Type.SURGERY_OPERATION, offset, limit);
    }

    public List<TherapyDTO> getFinishedPhysiotherapiesByPerformerId(long id, int offset,
                                                                    int limit) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(
                id, TherapyDTO.Type.PHYSIOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getFinishedPharmacotherapiesByPerformerId(long id, int offset,
                                                                      int limit) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(
                id, TherapyDTO.Type.PHARMACOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getCurrentSurgeryOperationsByPerformerId(long id, int offset,
                                                                     int limit) {
        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(
                id, TherapyDTO.Type.SURGERY_OPERATION, offset, limit);
    }

    public List<TherapyDTO> getCurrentPhysiotherapiesByPerformerId(long id, int offset,
                                                                   int limit) {

        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(
                id, TherapyDTO.Type.PHYSIOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getCurrentPharmacotherapiesByPerformerId(long id, int offset,
                                                                     int limit) {

        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(
                id, TherapyDTO.Type.PHARMACOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getFutureSurgeryOperationsByPerformerId(long id, int offset,
                                                                    int limit) {

        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(
                id, TherapyDTO.Type.SURGERY_OPERATION, offset, limit);
    }

    public List<TherapyDTO> getFuturePhysiotherapiesByPerformerId(long id, int offset,
                                                                  int limit) {

        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(
                id, TherapyDTO.Type.PHYSIOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getFuturePharmacotherapiesByPerformerId(long id, int offset,
                                                                    int limit) {

        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(
                id, TherapyDTO.Type.PHARMACOTHERAPY, offset, limit);
    }

    public List<TherapyDTO> getCurrentByPerformerId(long id, int offset, int limit) {
        return daoManager.getTherapyDao().findCurrentByPerformerId(id, offset, limit);
    }

    public long getCurrentByDoctorIdCount(long id) {
        return daoManager.getTherapyDao().findCurrentByPerformerIdCount(id);
    }

    public void performTherapy(long therapyId) {
        TherapyDAO therapyDao = daoManager.getTherapyDao();
        PatientDAO patientDAO = daoManager.getPatientDao();

        TherapyDTO therapyDTO =
                therapyDao.find(therapyId).orElseThrow(IllegalArgumentException::new);
        therapyDTO.setCompletionDateTime(Timestamp.from(Instant.now()));

        PatientDTO patientDTO =
                patientDAO.find(therapyDTO.getPatientId()).orElseThrow(IllegalStateException::new);
        patientDTO.setState(PatientDTO.State.APPLIED);

        daoManager.beginTransaction();
        therapyDao.update(therapyDTO);
        patientDAO.update(patientDTO);
        daoManager.finishTransaction();
    }

    public void changePerformer(long therapyId, long medicId) {
        TherapyDAO therapyDao = daoManager.getTherapyDao();
        TherapyDTO therapyDTO =
                therapyDao.find(therapyId).orElseThrow(IllegalArgumentException::new);
        therapyDTO.setPerformerId(medicId);
        therapyDao.update(therapyDTO);
    }

    @Override
    CrudDAO<TherapyDTO> getDAO() {
        return daoManager.getTherapyDao();
    }

}
