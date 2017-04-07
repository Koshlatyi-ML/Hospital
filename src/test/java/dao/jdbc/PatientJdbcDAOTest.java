package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.connection.TestingConnectionFactory;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Patient;
import domain.dto.PatientDTO;
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

public class PatientJdbcDAOTest {
    private PatientJdbcDAO jdbcDao;
    private PatientJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private PatientQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ConnectionManager realConnManag;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new PatientJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;

        when(realConnManag.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());

        PatientQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getPatientQueryExecutor();

        dao = new PatientJdbcDAO(queryExecutor, realConnManag);
    }

    @Test
    public void find() throws Exception {
        long id = 7;
        PatientDTO tested = dao.find(id).orElseThrow(Exception::new);

        PatientDTO desired = new PatientDTO.Builder()
                .setId(id)
                .setName("PatientName2")
                .setSurname("PatientSurname2")
                .setDoctorId(54)
                .setCompliants("Complaints2")
                .setDiagnosis(null)
                .setState(PatientDTO.State.ADDMITTED)
                .build();
        assertEquals(desired, tested);
        Thread.sleep(25);
    }

    @Test
    public void findAll() throws Exception {
        List<PatientDTO> all = dao.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void create() throws Exception {
        PatientDTO dto = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .build();

        dao.create(dto);

        PatientDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        PatientDTO dto = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<PatientDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        PatientDTO tested = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .build();
        dao.create(tested);

        tested.setName("name2");
        tested.setSurname("azaaz");
        tested.setDoctorId(55);
        tested.setDiagnosis("volc4ank@");

        dao.update(tested);
        PatientDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }

    @Test
    public void findByDepartmentId() throws Exception {
        List<PatientDTO> byDepartmentId = dao.findByDepartmentId(95);
        assertEquals(2, byDepartmentId.size());
    }

    @Test
    public void findByDoctorId() throws Exception {
        List<PatientDTO> byDoctorId = dao.findByDoctorId(54);
        assertEquals(2, byDoctorId.size());
    }

    @Test
    public void findByState() throws Exception {
        List<PatientDTO> daoByState = dao.findByState(Patient.State.DISCHARGED);
        assertEquals(1, daoByState.size());
    }

    @Test
    public void findByDepartmentIdNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDepartmentIdNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByDepartmentIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findByDoctorIdNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByDoctorId(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDoctorIdNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDoctorId(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDoctorIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDoctorId(100L);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findByStateIdNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByState(Patient.State.ADDMITTED);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByStateNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByState(Patient.State.ADDMITTED);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByStateTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByState(Patient.State.ADDMITTED);
        verify(connectionManagerMock).tryRollback();
    }
}