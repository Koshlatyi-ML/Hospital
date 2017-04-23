package service;

import dao.*;
import domain.CredentialsDTO;
import domain.DoctorDTO;
import service.dto.PatientRegistrationDTO;
import service.dto.StuffRegistrationDTO;

import java.util.List;
import java.util.Optional;

public class DoctorService extends AbstractCrudService<DoctorDTO>
        implements RegistrationService<StuffRegistrationDTO>, LoginService<DoctorDTO> {

    private DaoManager daoManager;

    DoctorService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public Optional<DoctorDTO> login(String login, String password) {
        return daoManager.getDoctorDao().findByLoginAndPassword(login, password);
    }

    @Override
    public DoctorDTO register(StuffRegistrationDTO registrationDTO) {
        DoctorDAO doctorDao = daoManager.getDoctorDao();
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setLogin(registrationDTO.getLogin())
                .setPassword(registrationDTO.getPassword())
                .build();
        DoctorDTO doctorDTO = new DoctorDTO.Builder()
                .setName(registrationDTO.getName())
                .setSurname(registrationDTO.getSurname())
                .setDepartmentId(registrationDTO.getDepartmentId())
                .build();

        daoManager.beginTransaction();

        credentialsDAO.create(credentialsDTO);
        doctorDTO.setCredentialsId(credentialsDTO.getId());
        doctorDao.create(doctorDTO);

        daoManager.finishTransaction();
        return doctorDTO;
    }

    public List<DoctorDTO> getByDepartmentId(long id, int offset, int limit) {
        return daoManager.getDoctorDao().findByDepartmentId(id, offset, limit);
    }

    public long getByDepartmentIdSize(long id) {
        return daoManager.getDoctorDao().findByDepartmentIdCount(id);
    }

    @Override
    CrudDAO<DoctorDTO> getDAO() {
        return daoManager.getDoctorDao();
    }
}
