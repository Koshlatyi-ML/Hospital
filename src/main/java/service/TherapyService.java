package service;

import dao.CrudDAO;
import dao.DaoManager;
import domain.TherapyDTO;

import java.util.List;

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

//    public void changePerformer(long performerId) {
//    ????????????????????????????????????????????????
//    }
//
//    public void completeTherapy() {
//    ????????????????????????????????????????????????
//    }

    @Override
    CrudDAO<TherapyDTO> getDAO() {
        return daoManager.getTherapyDao();
    }
}
