package service;

import dao.DaoManager;
import dao.exception.DaoException;
import dao.jdbc.TestJdbcDaoManager;
import domain.CredentialsDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class CredentialsServiceTest {

    @Test
    public void getSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        assertEquals(23, service.getSize());
    }

    @Test
    public void getAll() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        assertEquals(10, service.getAll(0, 10).size());
    }

    @Test
    public void get() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);

        int id = 7;
        CredentialsDTO dto =
                new CredentialsDTO.Builder()
                        .setId(id)
                        .setLogin("Nikolay")
                        .setPassword("password")
                        .build();

        assertEquals(dto, service.get(id).get());
    }

    @Test
    public void getEmpty() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        assertFalse(service.get(-1).isPresent());
    }

    @Test
    public void create() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        CredentialsDTO dto =
                new CredentialsDTO.Builder()
                        .setLogin("login")
                        .setPassword("password")
                        .build();
        service.create(dto);
        assertEquals(dto, service.get(dto.getId()).get());
        service.delete(dto.getId());
    }

    @Test
    public void update() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        String login = "testedLogin";
        String password = "testedPassword";

        CredentialsDTO credentialsDTO = service.get(7).get();
        String oldLogin = credentialsDTO.getLogin();
        String oldPassword = credentialsDTO.getPassword();

        credentialsDTO.setLogin(login);
        credentialsDTO.setPassword(password);
        service.update(credentialsDTO);

        CredentialsDTO dto =
                new CredentialsDTO.Builder()
                        .setId(credentialsDTO.getId())
                        .setLogin(login)
                        .setPassword(password)
                        .build();
        assertEquals(dto, service.get(credentialsDTO.getId()).get());

        credentialsDTO.setLogin(oldLogin);
        credentialsDTO.setPassword(oldPassword);
        service.update(credentialsDTO);
    }

    @Test(expected = DaoException.class)
    public void deleteRestrict() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        service.delete(7);
    }

    @Test
    public void delete() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);

        CredentialsDTO dto = new CredentialsDTO.Builder()
                .setPassword("pswd")
                .setLogin("login")
                .build();

        service.create(dto);
        service.delete(dto.getId());
        assertFalse(service.get(dto.getId()).isPresent());
    }


    @Test
    public void hasLogin() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        CredentialsService service = new CredentialsService(daoManagerMock);
        assertTrue(service.hasLogin("Nikolay"));
    }
}