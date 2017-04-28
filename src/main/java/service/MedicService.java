package service;

import dao.*;
import domain.CredentialsDTO;
import domain.DoctorDTO;
import domain.MedicDTO;
import service.dto.StuffRegistrationDTO;

import java.util.List;
import java.util.Optional;

public class MedicService extends AbstractCrudService<MedicDTO>
        implements RegistrationService<StuffRegistrationDTO>, LoginService<MedicDTO> {

    private DaoManager daoManager;

    MedicService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public void delete(long id) {
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();
        MedicDAO medicDAO = daoManager.getMedicDao();
        Optional<MedicDTO> medicDTO = medicDAO.find(id);
        medicDTO.ifPresent(medic -> {
            long credentialsId = medic.getCredentialsId();
            daoManager.beginTransaction();
            medicDAO.delete(id);
            credentialsDAO.delete(credentialsId);
            daoManager.finishTransaction();
        });
    }

    @Override
    public Optional<MedicDTO> login(String login, String password) {
        return daoManager.getMedicDao().findByLoginAndPassword(login, password);
    }

    @Override
    public MedicDTO register(StuffRegistrationDTO registrationDTO) {
        MedicDAO medicDAO = daoManager.getMedicDao();
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setLogin(registrationDTO.getLogin())
                .setPassword(registrationDTO.getPassword())
                .build();
        MedicDTO medicDTO = new MedicDTO.Builder()
                .setName(registrationDTO.getName())
                .setSurname(registrationDTO.getSurname())
                .setDepartmentId(registrationDTO.getDepartmentId())
                .build();

        daoManager.beginTransaction();

        credentialsDAO.create(credentialsDTO);
        medicDTO.setCredentialsId(credentialsDTO.getId());
        medicDAO.create(medicDTO);

        daoManager.finishTransaction();
        return medicDTO;
    }

    public List<MedicDTO> getByDepartmentId(long id, int offset, int limit) {
        return daoManager.getMedicDao().findByDepartmentId(id, offset, limit);
    }

    public long getByDepartmentIdSize(long id) {
        return daoManager.getMedicDao().findByDepartmentIdCount(id);
    }


    public List<MedicDTO> getWithoutDepartmentId(int offset, int limit) {
        return daoManager.getMedicDao().findWithoutDepartmentId(offset, limit);
    }

    public long getWithoutDepartmentIdSize() {
        return daoManager.getMedicDao().findWithoutDepartmentIdSize();
    }

    @Override
    CrudDAO<MedicDTO> getDAO() {
        return daoManager.getMedicDao();
    }
}
