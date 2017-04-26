package service;

import dao.*;
import domain.CredentialsDTO;
import domain.DoctorDTO;
import domain.PatientDTO;
import domain.TherapyDTO;
import service.dto.PatientApplicationDTO;
import service.dto.PatientRegistrationDTO;
import service.dto.StuffRegistrationDTO;
import service.dto.TherapyPrescriptionDTO;

import java.util.List;
import java.util.Optional;

public class DoctorService extends AbstractCrudService<DoctorDTO>
        implements RegistrationService<StuffRegistrationDTO>, LoginService<DoctorDTO> {

    private DaoManager daoManager;

    DoctorService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public void delete(long id) {
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();
        DoctorDAO doctorDAO = daoManager.getDoctorDao();
        Optional<DoctorDTO> doctorDTO = doctorDAO.find(id);
        doctorDTO.ifPresent(doc -> {
            long credentialsId = doc.getCredentialsId();
            daoManager.beginTransaction();
            doctorDAO.delete(id);
            credentialsDAO.delete(credentialsId);
            daoManager.finishTransaction();
        });
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
