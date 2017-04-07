package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.connection.TestingConnectionFactory;
import dao.jdbc.query.DoctorQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.dto.DoctorDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorJdbcDAOTest {
    private DoctorJdbcDAO jdbcDao;
    private DoctorJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private DoctorQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ConnectionManager realConnManag;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new DoctorJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;

        when(realConnManag.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());

        DoctorQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getDoctorQueryExecutor();

        dao = new DoctorJdbcDAO(queryExecutor, realConnManag);
    }

    @Test
    public void find() throws Exception {
        DoctorDTO tested = dao.find(54).orElseThrow(Exception::new);

        DoctorDTO desired = new DoctorDTO.Builder()
                .setId(tested.getId())
                .setName("DoctorName1")
                .setSurname("DoctorSurname1")
                .setDepartmentId(95)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<DoctorDTO> all = dao.findAll();
        assertEquals(8, all.size());
    }

    @Test
    public void create() throws Exception {
        DoctorDTO dto = new DoctorDTO.Builder()
                .setName("name")
                .setSurname("surname")
                .setDepartmentId(95)
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
                .build();

        assertEquals(desired, tested);
    }

    @Test
    public void findByPatientIdNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByPatientIdNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByPatientIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionManagerMock).tryRollback();
    }


}