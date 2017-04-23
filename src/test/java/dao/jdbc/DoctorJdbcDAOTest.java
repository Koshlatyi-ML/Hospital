package dao.jdbc;

import dao.connection.TestConnectionProvider;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.DoctorDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.when;

public class DoctorJdbcDAOTest {
    private DoctorJdbcDAO jdbcDao;
    private DoctorJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private DoctorQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());

        jdbcDao = new DoctorJdbcDAO(queryExecutorMock, connectionManager);

        DoctorQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getDoctorQueryExecutor();

        dao = new DoctorJdbcDAO(queryExecutor, connectionManager);
    }

    @Test
    public void find() throws Exception {
        DoctorDTO tested = dao.find(54).orElseThrow(Exception::new);

        DoctorDTO desired = new DoctorDTO.Builder()
                .setId(tested.getId())
                .setName("DoctorName1")
                .setSurname("DoctorSurname1")
                .setDepartmentId(95)
                .setCredentialsId(1)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void count() throws Exception {
        assertEquals(8, dao.count());
    }

    @Test
    public void findAll() throws Exception {
        List<DoctorDTO> all = dao.findAll(3, 5);
        assertEquals(5, all.size());
    }

    @Test
    public void create() throws Exception {
        DoctorDTO dto = new DoctorDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(21)
                .build();

        dao.create(dto);

        DoctorDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        DoctorDTO dto = new DoctorDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
                .setCredentialsId(21)
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<DoctorDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        DoctorDTO tested = new DoctorDTO.Builder()
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
        DoctorDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }

    @Test
    public void findByPatientId() throws Exception {
        DoctorDTO tested = dao.findByPatientId(12).orElseThrow(Exception::new);

        DoctorDTO desired = new DoctorDTO.Builder()
                .setId(55)
                .setName("DoctorName2")
                .setSurname("DoctorSurname2")
                .setDepartmentId(96)
                .setCredentialsId(2)
                .build();

        assertEquals(desired, tested);
    }

    @Test
    public void findByLoginAndPassword() throws Exception {
        DoctorDTO tested = dao.findByLoginAndPassword("aaa", "wadw")
                .orElseThrow(Exception::new);

        DoctorDTO desired = new DoctorDTO.Builder()
                .setId(55)
                .setName("DoctorName2")
                .setSurname("DoctorSurname2")
                .setDepartmentId(96)
                .setCredentialsId(2)
                .build();

        assertEquals(desired, tested);
    }

    @Test
    public void findByCredentialsId() throws Exception {
        DoctorDTO tested = dao.findByCredentialsId(2).orElseThrow(Exception::new);

        DoctorDTO desired = new DoctorDTO.Builder()
                .setId(55)
                .setName("DoctorName2")
                .setSurname("DoctorSurname2")
                .setDepartmentId(96)
                .setCredentialsId(2)
                .build();

        assertEquals(desired, tested);
    }
}