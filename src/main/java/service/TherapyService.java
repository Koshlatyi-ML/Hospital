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

    public List<TherapyDTO> getFinishedByPatientId(long patientId) {
        return daoManager.getTherapyDao().findFinishedByPatientId(patientId);
    }

    public List<TherapyDTO> getNotFinishedByPatientId(long patientId) {
        return daoManager.getTherapyDao().findNotFinishedByPatientId(patientId);
    }

    public List<TherapyDTO> getFinishedSurgeryOperationsByPerformerId(long id) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(id,
                TherapyDTO.Type.SURGERY_OPERATION);
    }

    public List<TherapyDTO> getFinishedPhysiotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(id,
                TherapyDTO.Type.PHYSIOTHERAPY);
    }

    public List<TherapyDTO> getFinishedPharmacotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findFinishedByPerformerIdAndType(id,
                TherapyDTO.Type.PHARMACOTHERAPY);
    }

    public List<TherapyDTO> getCurrentSurgeryOperationsByPerformerId(long id) {
        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(id,
                TherapyDTO.Type.SURGERY_OPERATION);
    }

    public List<TherapyDTO> getCurrentPhysiotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(id,
                TherapyDTO.Type.PHYSIOTHERAPY);
    }

    public List<TherapyDTO> getCurrentPharmacotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findCurrentByPerformerIdAndType(id,
                TherapyDTO.Type.PHARMACOTHERAPY);
    }

    public List<TherapyDTO> getFutureSurgeryOperationsByPerformerId(long id) {
        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(id,
                TherapyDTO.Type.SURGERY_OPERATION);
    }

    public List<TherapyDTO> getFuturePhysiotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(id,
                TherapyDTO.Type.PHYSIOTHERAPY);
    }

    public List<TherapyDTO> getFuturePharmacotherapiesByPerformerId(long id) {
        return daoManager.getTherapyDao().findFutureByPerformerIdAndType(id,
                TherapyDTO.Type.PHARMACOTHERAPY);
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
