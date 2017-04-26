package service;

import dao.DaoManager;
import dao.exception.DaoException;
import dao.jdbc.TestJdbcDaoManager;
import domain.CredentialsDTO;
import domain.DoctorDTO;
import org.junit.Test;
import service.dto.StuffRegistrationDTO;

import static org.junit.Assert.*;

public class DoctorServiceTest {

    @Test
    public void getSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService service = new DoctorService(daoManagerMock);
        assertEquals(8, service.getSize());
    }

    @Test
    public void getAll() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService service = new DoctorService(daoManagerMock);
        assertEquals(5, service.getAll(0, 5).size());
    }

    @Test
    public void get() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);

        DoctorDTO dto =
                new DoctorDTO.Builder()
                        .setId(54)
                        .setName("DoctorName1")
                        .setSurname("DoctorSurname1")
                        .setDepartmentId(95)
                        .setCredentialsId(1)
                        .build();

        assertEquals(dto, doctorService.get(54).get());
    }

    @Test
    public void getEmpty() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService service = new DoctorService(daoManagerMock);
        assertFalse(service.get(-1).isPresent());
    }

    @Test
    public void create() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        DoctorDTO dto =
                new DoctorDTO.Builder()
                        .setName("name")
                        .setSurname("surname")
                        .setDepartmentId(95)
                        .setCredentialsId(credentialsDTO.getId())
                        .build();

        doctorService.create(dto);
        assertEquals(dto, doctorService.get(dto.getId()).get());
        doctorService.delete(dto.getId());
    }

    @Test
    public void update() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService service = new DoctorService(daoManagerMock);
        String name = "testedName";
        String surname = "testedSurname";

        DoctorDTO doctorDTO = service.get(54).get();
        String oldName = doctorDTO.getName();
        String oldSurname = doctorDTO.getSurname();

        doctorDTO.setName(name);
        doctorDTO.setSurname(surname);
        service.update(doctorDTO);

        DoctorDTO dto =
                new DoctorDTO.Builder()
                        .setId(doctorDTO.getId())
                        .setName(name)
                        .setSurname(surname)
                        .setDepartmentId(95)
                        .setCredentialsId(1)
                        .build();
        assertEquals(dto, service.get(doctorDTO.getId()).get());

        doctorDTO.setName(oldName);
        doctorDTO.setSurname(oldSurname);
        service.update(doctorDTO);
    }

    @Test(expected = DaoException.class)
    public void deleteRestrict() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService service = new DoctorService(daoManagerMock);

        DoctorDTO dto = service.get(54).get();
        service.delete(dto.getId());
    }

    @Test
    public void deleteSuccess() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        DoctorDTO dto = new DoctorDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        doctorService.create(dto);
        doctorService.delete(dto.getId());
        assertFalse(doctorService.get(dto.getId()).isPresent());
    }

    @Test
    public void deleteNotIdleCredentials() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        DoctorDTO dto = new DoctorDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        doctorService.create(dto);
        doctorService.delete(dto.getId());
        assertFalse(credentialsService.get(credentialsDTO.getId()).isPresent());
    }

    @Test
    public void getByDepartmentId() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        assertEquals(2, doctorService.getByDepartmentId(95, 0, 10).size());
    }

    @Test
    public void getByDepartmentIdSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        assertEquals(2, doctorService.getByDepartmentIdSize(95));
    }

    @Test
    public void login() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        DoctorDTO dto = new DoctorDTO.Builder()
                .setId(54)
                .setName("DoctorName1")
                .setSurname("DoctorSurname1")
                .setDepartmentId(95)
                .setCredentialsId(1)
                .build();

        assertEquals(dto, doctorService.login("kopile", "1111").get());
    }

    @Test
    public void register() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DoctorService doctorService = new DoctorService(daoManagerMock);
        String name = "newName";
        String surname = "newSurname";
        String login = "newLogin";
        String password = "newPassword";
        long departmentId = 95;

        StuffRegistrationDTO registrationDTO =
                new StuffRegistrationDTO.Builder()
                .setName(name)
                .setSurname(surname)
                .setLogin(login)
                .setPassword(password)
                .setDepartmentId(departmentId)
                .build();

        DoctorDTO registered = doctorService.register(registrationDTO);
        assertEquals(registered, doctorService.login(login, password).get());
        doctorService.delete(registered.getId());
    }
}