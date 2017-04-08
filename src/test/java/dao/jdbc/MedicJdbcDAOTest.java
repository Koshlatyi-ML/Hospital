package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.connection.TestingConnectionFactory;
import dao.jdbc.query.MedicQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.dto.DoctorDTO;
import domain.dto.MedicDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MedicJdbcDAOTest {
    private MedicJdbcDAO jdbcDao;
    private MedicJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private MedicQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ConnectionManager realConnManag;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new MedicJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;

        when(realConnManag.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());

        MedicQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getMedicQueryExecutor();

        dao = new MedicJdbcDAO(queryExecutor, realConnManag);
    }

    @Test
    public void find() throws Exception {
        MedicDTO tested = dao.find(62).orElseThrow(Exception::new);

        MedicDTO desired = new MedicDTO.Builder()
                .setId(tested.getId())
                .setName("MedicName1")
                .setSurname("MedicSurname1")
                .setDepartmentId(95)
                .setCredentialsId(9)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<MedicDTO> all = dao.findAll();
        assertEquals(8, all.size());
    }

    @Test
    public void create() throws Exception {
        MedicDTO dto = new MedicDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(21)
                .build();

        dao.create(dto);

        MedicDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        MedicDTO dto = new MedicDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(21)
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<MedicDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        MedicDTO tested = new MedicDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(21)
                .build();
        dao.create(tested);

        tested.setName("name2");
        tested.setSurname("azaaz");
        tested.setDepartmentId(96);

        dao.update(tested);
        MedicDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }

    @Test
    public void findByCredentialsId() throws Exception {
        MedicDTO tested = dao.findByCredentialsId(9).orElseThrow(Exception::new);

        MedicDTO desired = new MedicDTO.Builder()
                .setId(tested.getId())
                .setName("MedicName1")
                .setSurname("MedicSurname1")
                .setDepartmentId(95)
                .setCredentialsId(9)
                .build();

        assertEquals(desired, tested);
    }
}