package service;

import dao.DaoManager;
import dao.jdbc.TestJdbcDaoManager;
import domain.CredentialsDTO;
import domain.MedicDTO;
import org.junit.Test;
import service.dto.StuffRegistrationDTO;

import static org.junit.Assert.*;

public class MedicServiceTest {

    @Test
    public void getSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        assertEquals(8, service.getSize());
    }

    @Test
    public void getAll() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        assertEquals(5, service.getAll(0, 5).size());
    }

    @Test
    public void get() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService medicService = new MedicService(daoManagerMock);

        MedicDTO dto =
                new MedicDTO.Builder()
                        .setId(62)
                        .setName("MedicName1")
                        .setSurname("MedicSurname1")
                        .setDepartmentId(95)
                        .setCredentialsId(9)
                        .build();

        assertEquals(dto, medicService.get(62).get());
    }

    @Test
    public void getEmpty() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        assertFalse(service.get(-1).isPresent());
    }

    @Test
    public void create() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService medicService = new MedicService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        MedicDTO dto =
                new MedicDTO.Builder()
                        .setName("name")
                        .setSurname("surname")
                        .setDepartmentId(95)
                        .setCredentialsId(credentialsDTO.getId())
                        .build();

        medicService.create(dto);
        assertEquals(dto, medicService.get(dto.getId()).get());
        medicService.delete(dto.getId());
    }

    @Test
    public void update() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        String name = "testedName";
        String surname = "testedSurname";

        MedicDTO MedicDTO = service.get(62).get();
        String oldName = MedicDTO.getName();
        String oldSurname = MedicDTO.getSurname();

        MedicDTO.setName(name);
        MedicDTO.setSurname(surname);
        service.update(MedicDTO);

        MedicDTO dto =
                new MedicDTO.Builder()
                        .setId(MedicDTO.getId())
                        .setName(name)
                        .setSurname(surname)
                        .setDepartmentId(95)
                        .setCredentialsId(9)
                        .build();
        assertEquals(dto, service.get(MedicDTO.getId()).get());

        MedicDTO.setName(oldName);
        MedicDTO.setSurname(oldSurname);
        service.update(MedicDTO);
    }

    @Test
    public void deleteSuccess() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService medicService = new MedicService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        MedicDTO dto = new MedicDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        medicService.create(dto);
        medicService.delete(dto.getId());
        assertFalse(medicService.get(dto.getId()).isPresent());
    }

    @Test
    public void deleteNotIdleCredentials() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService medicService = new MedicService(daoManagerMock);
        CredentialsService credentialsService = new CredentialsService(daoManagerMock);

        CredentialsDTO credentialsDTO = new CredentialsDTO.Builder()
                .setPassword("pwd")
                .setLogin("lgn")
                .build();
        credentialsService.create(credentialsDTO);

        MedicDTO dto = new MedicDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(credentialsDTO.getId())
                .build();

        medicService.create(dto);
        medicService.delete(dto.getId());
        assertFalse(credentialsService.get(credentialsDTO.getId()).isPresent());
    }

    @Test
    public void getByDepartmentId() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        assertEquals(2, service.getByDepartmentId(95, 0, 10).size());
    }

    @Test
    public void getByDepartmentIdSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        assertEquals(2, service.getByDepartmentIdSize(95));
    }

    @Test
    public void login() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
        MedicDTO dto = new MedicDTO.Builder()
                .setId(62)
                .setName("MedicName1")
                .setSurname("MedicSurname1")
                .setDepartmentId(95)
                .setCredentialsId(9)
                .build();

        assertEquals(dto, service.login("tyuio", "vbn").get());
    }

    @Test
    public void register() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        MedicService service = new MedicService(daoManagerMock);
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

        MedicDTO registered = service.register(registrationDTO);
        assertEquals(registered, service.login(login, password).get());
        service.delete(registered.getId());
    }
}