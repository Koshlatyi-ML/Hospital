package service;

import dao.*;
import domain.CredentialsDTO;
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
    public Optional<MedicDTO> login(String login, String password) {
        return daoManager.getMedicDao().findByLoginAndPassword(login, password);
    }

    @Override
    public void register(StuffRegistrationDTO registrationDTO) {
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
    }

    public List<MedicDTO> getByDepartmentId(long id) {
        return daoManager.getMedicDao().findByDepartmentId(id);
    }

    @Override
    CrudDAO<MedicDTO> getDAO() {
        return daoManager.getMedicDao();
    }
}
