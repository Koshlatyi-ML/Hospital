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

    public void prescribeTherapy(long doctorId, long patientId, TherapyPrescriptionDTO prescriptionDTO) {
        TherapyDTO therapyDTO = new TherapyDTO.Builder()
                .setTitle(prescriptionDTO.getTitle())
                .setType(TherapyDTO.Type.valueOf(prescriptionDTO.getType()))
                .setDescription(prescriptionDTO.getDescription())
                .setAppointmentDateTime(prescriptionDTO.getAppointmentDateTime())
                .setPatientId(patientId)
                .setPerformerId(doctorId)
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

    @Override
    CrudDAO<DoctorDTO> getDAO() {
        return daoManager.getDoctorDao();
    }
}