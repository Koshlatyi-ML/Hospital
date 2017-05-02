package service;

import dao.DaoManager;
import dao.jdbc.TestJdbcDaoManager;
import domain.CredentialsDTO;
import domain.PatientDTO;
import domain.TherapyDTO;
import org.junit.Test;
import service.dto.PatientApplicationDTO;
import service.dto.PatientRegistrationDTO;
import service.dto.TherapyPrescriptionDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class PatientServiceTest {

    @Test
    public void getSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(4, service.getSize());
    }

    @Test
    public void getAll() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(4, service.getAll(0, 5).size());
    }

    @Test
    public void get() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);

        PatientDTO dto =
                new PatientDTO.Builder()
                        .setId(7)
                        .setName("PatientName2")
                        .setSurname("PatientSurname2")
                        .setDoctorId(54)
                        .setCompliants("Complaints2")
                        .setState(PatientDTO.State.TREATED)
                        .setCredentialsId(18)
                        .build();

        assertEquals(dto, patientService.get(7).get());
    }

    @Test
    public void getEmpty() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertFalse(service.get(-1).isPresent());
    }

    @Test
    public void create() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        PatientDTO dto =
                new PatientDTO.Builder()
                        .setName("name")
                        .setSurname("surname")
                        .setCompliants("complaints")
                        .setState(PatientDTO.State.APPLIED)
                        .setDoctorId(54)
                        .setCredentialsId(credentialsDTO.getId())
                        .build();

        patientService.create(dto);
        assertEquals(dto, patientService.get(dto.getId()).get());
        patientService.delete(dto.getId());
    }

    @Test
    public void update() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        String name = "testedName";
        String surname = "testedSurname";

        PatientDTO patientDTO = service.get(7).get();
        String oldName = patientDTO.getName();
        String oldSurname = patientDTO.getSurname();


        patientDTO.setName(name);
        patientDTO.setSurname(surname);
        service.update(patientDTO);

        PatientDTO dto =
                new PatientDTO.Builder()
                        .setId(patientDTO.getId())
                        .setName(name)
                        .setSurname(surname)
                        .setDoctorId(54)
                        .setCompliants("Complaints2")
                        .setState(PatientDTO.State.TREATED)
                        .setCredentialsId(18)
                        .build();
        assertEquals(dto, service.get(patientDTO.getId()).get());

        patientDTO.setName(oldName);
        patientDTO.setSurname(oldSurname);
        service.update(patientDTO);
    }

    @Test
    public void deleteSuccess() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        PatientDTO dto = new PatientDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setState(PatientDTO.State.REGISTERED)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        patientService.create(dto);
        patientService.delete(dto.getId());
        assertFalse(patientService.get(dto.getId()).isPresent());
    }

    @Test
    public void deleteNotIdleCredentials() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        PatientDTO dto = new PatientDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setState(PatientDTO.State.REGISTERED)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        patientService.create(dto);
        patientService.delete(dto.getId());
        assertFalse(credentialsService.get(credentialsDTO.getId()).isPresent());
    }

    @Test
    public void getAppliedPatientsOfDoctor() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(1, service.getAppliedPatientsOfDoctor(54, 0, 10).size());
    }

    @Test
    public void getAppliedPatientsOfDoctorSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(1, service.getAppliedPatientsOfDoctorSize(54));
    }

    @Test
    public void getTreatedPatientsOfDoctor() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(1, service.getTreatedPatientsOfDoctor(54, 0, 10).size());
    }

    @Test
    public void getTreatedPatientsOfDoctorSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        assertEquals(1, service.getTreatedPatientsOfDoctorSize(54));
    }

    @Test
    public void applyToDoctor() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);

        Optional<PatientDTO> patientDTO = service.get(8);
        PatientApplicationDTO applicationDTO =
                new PatientApplicationDTO.Builder()
                .setComplaints("New complaints")
                .setDoctorId(55)
                .build();

        service.applyToDoctor(patientDTO.get(), applicationDTO);
        PatientApplicationDTO appliedDTO =
                new PatientApplicationDTO.Builder()
                        .setComplaints(patientDTO.get().getComplaints())
                        .setDoctorId(patientDTO.get().getDoctorId())
                        .build();

        assertEquals(applicationDTO, appliedDTO);
        PatientDTO dto = patientDTO.get();
        dto.setState(PatientDTO.State.DISCHARGED);
        dto.setDoctorId(0);
        dto.setComplaints(null);
        service.update(dto);
    }

    @Test
    public void discharge() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);

        String diagnosis = "diagnosis";

        patientService.discharge(7, diagnosis);
        PatientDTO patientDTO = patientService.get(7).get();

        assertEquals(diagnosis, patientDTO.getDiagnosis());

        patientDTO.setDoctorId(54);
        patientDTO.setComplaints("Complaints2");
        patientDTO.setState(PatientDTO.State.TREATED);
        patientDTO.setDiagnosis(null);
        patientService.update(patientDTO);
    }

    @Test
    public void prescribeTherapy() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService patientService = new PatientService(daoManagerMock);
        TherapyService therapyService = new TherapyService(daoManagerMock);

        int size = therapyService.getNotFinishedByPatientId(12, 0, 1000).size();
        LocalDateTime time = LocalDateTime.of(2017, 5, 10, 12, 0, 0);
        TherapyPrescriptionDTO prescriptionDTO =
                new TherapyPrescriptionDTO.Builder()
                .setDescription("description")
                .setPerformerId(54)
                .setTitle("title")
                .setType(TherapyDTO.Type.SURGERY_OPERATION.toString())
                .setAppointmentDateTime(Timestamp.valueOf(time))
                .build();
        patientService.prescribeTherapy(12, prescriptionDTO);

        assertEquals(size + 1, therapyService.getNotFinishedByPatientId(12, 0, 1000).size());
        PatientDTO patientDTO = patientService.get(12).get();
        patientDTO.setState(PatientDTO.State.APPLIED);
        patientService.update(patientDTO);
        therapyService.delete(therapyService.getNotFinishedByPatientId(12, 0, 100).get(size).getId());
    }

    @Test
    public void login() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        PatientDTO dto =
                new PatientDTO.Builder()
                        .setId(7)
                        .setName("PatientName2")
                        .setSurname("PatientSurname2")
                        .setDoctorId(54)
                        .setCompliants("Complaints2")
                        .setState(PatientDTO.State.TREATED)
                        .setCredentialsId(18)
                        .build();

        assertEquals(dto, service.login("ppol", "6666").get());
    }

    @Test
    public void register() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        PatientService service = new PatientService(daoManagerMock);
        String name = "newName";
        String surname = "newSurname";
        String login = "newLogin";
        String password = "newPassword";

        PatientRegistrationDTO registrationDTO =
                new PatientRegistrationDTO.Builder()
                        .setName(name)
                        .setSurname(surname)
                        .setLogin(login)
                        .setPassword(password)
                        .build();

        PatientDTO registered = service.register(registrationDTO);
        assertEquals(registered, service.login(login, password).get());
        service.delete(registered.getId());
    }
}