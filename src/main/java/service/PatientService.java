package service;

import dao.CredentialsDAO;
import dao.CrudDAO;
import dao.DaoManager;
import dao.PatientDAO;
import domain.CredentialsDTO;
import domain.PatientDTO;
import service.dto.PatientRegistrationDTO;

import java.util.List;
import java.util.Optional;

public class PatientService extends AbstractCrudService<PatientDTO>
        implements RegistrationService<PatientRegistrationDTO>, LoginService<PatientDTO> {

    private DaoManager daoManager;

    PatientService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public Optional<PatientDTO> login(String login, String password) {
        return daoManager.getPatientDao().findByLoginAndPassword(login, password);

    }

    @Override
    public void register(PatientRegistrationDTO registrationDTO) {
        PatientDAO patientDAO = daoManager.getPatientDao();
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setLogin(registrationDTO.getLogin())
                .setPassword(registrationDTO.getPassword())
                .build();
        PatientDTO patientDTO = new PatientDTO.Builder().build();

        daoManager.beginTransaction();

        credentialsDAO.create(credentialsDTO);
        patientDTO.setCredentialsId(credentialsDTO.getId());
        patientDAO.create(patientDTO);

        daoManager.finishTransaction();
    }

    public List<PatientDTO> getAppliedPatientsOfDoctor(long doctorId) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndState(doctorId, PatientDTO.State.APPLIED);
    }

    public List<PatientDTO> getTreatedPatientsOfDoctor(long doctorId) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndState(doctorId, PatientDTO.State.TREATED);
    }

    @Override
    CrudDAO<PatientDTO> getDAO() {
        return daoManager.getPatientDao();
    }
}
