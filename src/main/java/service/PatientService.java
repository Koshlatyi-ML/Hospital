package service;

import dao.*;
import domain.CredentialsDTO;
import domain.DoctorDTO;
import domain.PatientDTO;
import service.dto.AbstractRegistrationDTO;
import service.dto.PatientApplicationDTO;
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
    public PatientDTO register(PatientRegistrationDTO registrationDTO) {
        PatientDAO patientDAO = daoManager.getPatientDao();
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setLogin(registrationDTO.getLogin())
                .setPassword(registrationDTO.getPassword())
                .build();
        PatientDTO patientDTO = new PatientDTO.Builder()
                .setName(registrationDTO.getName())
                .setSurname(registrationDTO.getSurname())
                .build();

        daoManager.beginTransaction();

        credentialsDAO.create(credentialsDTO);
        patientDTO.setCredentialsId(credentialsDTO.getId());
        patientDAO.create(patientDTO);

        daoManager.finishTransaction();
        return patientDTO;
    }

    public void applyToDoctor(PatientDTO patient, PatientApplicationDTO dto) {
        patient.setDoctorId(dto.getDoctorId());
        patient.setComplaints(dto.getComplaints());
        patient.setState(PatientDTO.State.APPLIED);
        daoManager.getPatientDao().update(patient);
    }

    public List<PatientDTO> getAppliedPatientsOfDoctor(long doctorId, int offset, int limit) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndState(doctorId, PatientDTO.State.APPLIED, offset, limit);
    }

    public List<PatientDTO> getTreatedPatientsOfDoctor(long doctorId, int offset, int limit) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndState(doctorId, PatientDTO.State.TREATED, offset, limit);
    }

    @Override
    CrudDAO<PatientDTO> getDAO() {
        return daoManager.getPatientDao();
    }
}
