package service;

import dao.*;
import domain.CredentialsDTO;
import domain.MedicDTO;
import domain.PatientDTO;
import domain.TherapyDTO;
import service.dto.PatientApplicationDTO;
import service.dto.PatientRegistrationDTO;
import service.dto.TherapyPrescriptionDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class PatientService extends AbstractCrudService<PatientDTO>
        implements RegistrationService<PatientRegistrationDTO>, LoginService<PatientDTO> {

    private DaoManager daoManager;

    PatientService(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public void delete(long id) {
        CredentialsDAO credentialsDAO = daoManager.getCredentialsDao();
        PatientDAO patientDAO = daoManager.getPatientDao();
        Optional<PatientDTO> patientDTO = patientDAO.find(id);
        patientDTO.ifPresent(medic -> {
            long credentialsId = medic.getCredentialsId();
            daoManager.beginTransaction();
            patientDAO.delete(id);
            credentialsDAO.delete(credentialsId);
            daoManager.finishTransaction();
        });
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

    public long getAppliedPatientsOfDoctorSize(long doctorId) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndStateCount(doctorId, PatientDTO.State.APPLIED);
    }

    public List<PatientDTO> getTreatedPatientsOfDoctor(long doctorId, int offset, int limit) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndState(doctorId, PatientDTO.State.TREATED, offset, limit);
    }

    public long getTreatedPatientsOfDoctorSize(long doctorId) {
        return daoManager.getPatientDao()
                .findByDoctorIdAndStateCount(doctorId, PatientDTO.State.TREATED);
    }

    public void prescribeTherapy(long patientId, TherapyPrescriptionDTO prescriptionDTO) {
        TherapyDTO therapyDTO = new TherapyDTO.Builder()
                .setTitle(prescriptionDTO.getTitle())
                .setType(TherapyDTO.Type.valueOf(prescriptionDTO.getType()))
                .setDescription(prescriptionDTO.getDescription())
                .setAppointmentDateTime(prescriptionDTO.getAppointmentDateTime())
                .setPatientId(patientId)
                .setPerformerId(prescriptionDTO.getPerformerId())
                .build();
        TherapyDAO therapyDao = daoManager.getTherapyDao();
        PatientDAO patientDao = daoManager.getPatientDao();

        daoManager.beginTransaction();
        therapyDao.create(therapyDTO);
        PatientDTO patientDTO = patientDao.find(patientId).orElseThrow(IllegalArgumentException::new);
        patientDTO.setState(PatientDTO.State.TREATED);
        patientDao.update(patientDTO);
        daoManager.finishTransaction();
    }

    public void discharge(long therapyId, String diagnosis) {
        TherapyDAO therapyDAO = daoManager.getTherapyDao();
        PatientDAO patientDAO = daoManager.getPatientDao();

        TherapyDTO therapyDTO =
                therapyDAO.find(therapyId).orElseThrow(IllegalArgumentException::new);
        therapyDTO.setCompletionDateTime(Timestamp.from(Instant.now()));

        long patientId = therapyDTO.getPatientId();
        PatientDTO patientDTO =
                patientDAO.find(patientId).orElseThrow(IllegalStateException::new);

        patientDTO.setDiagnosis(diagnosis);
        patientDTO.setState(PatientDTO.State.DISCHARGED);
        patientDTO.setDoctorId(0);
        patientDTO.setComplaints(null);

        daoManager.beginTransaction();
        therapyDAO.update(therapyDTO);
        patientDAO.update(patientDTO);
        daoManager.finishTransaction();
    }
    @Override
    CrudDAO<PatientDTO> getDAO() {
        return daoManager.getPatientDao();
    }

}
