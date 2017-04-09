package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.connection.TestingConnectionFactory;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.dto.PatientDTO;
import domain.dto.TherapyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TherapyJdbcDAOTest {
    private TherapyJdbcDAO jdbcDao;
    private TherapyJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private TherapyQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ConnectionManager realConnManag;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new TherapyJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;

        when(realConnManag.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());

        TherapyQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getTherapyQueryExecutor();

        dao = new TherapyJdbcDAO(queryExecutor, realConnManag);
    }

    @Test
    public void find() throws Exception {
        long id = 11;
        TherapyDTO tested = dao.find(id).orElseThrow(Exception::new);

        TherapyDTO desired = new TherapyDTO.Builder()
                .setId(id)
                .setTitle("Title1")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("Description1")
                .setAppointmentDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 23, 54)))
                .setCompleteDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 33, 54)))
                .setPatientId(6)
                .setPerformerId(55)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<TherapyDTO> all = dao.findAll();
        assertEquals(8, all.size());
    }

    @Test
    public void create() throws Exception {
        TherapyDTO dto = new TherapyDTO.Builder()
                .setTitle("sdfsf")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("sdsgsg")
                .setAppointmentDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 23, 54)))
                .setCompleteDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 33, 54)))
                .setPatientId(6)
                .setPerformerId(55)
                .build();

        dao.create(dto);

        TherapyDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        TherapyDTO dto = new TherapyDTO.Builder()
                .setTitle("sdfsf")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("sdsgsg")
                .setAppointmentDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 23, 54)))
                .setCompleteDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2016, 10, 19, 10, 33, 54)))
                .setPatientId(6)
                .setPerformerId(55)
                .build();


        dao.create(dto);
        dao.delete(dto);
        Optional<TherapyDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void findCurrentByPerformerIdAndType() throws Exception {
        List<TherapyDTO> currentByPerformerIdAndType =
                dao.findCurrentByPerformerIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByPerformerIdAndType.size());
    }

    @Test
    public void findCurrentByPatientIdAndType() throws Exception {
        List<TherapyDTO> currentByPatientIdAndType =
                dao.findCurrentByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByPatientIdAndType.size());
    }

    @Test
    public void findFinishedByPerformerIdAndType() throws Exception {
        List<TherapyDTO> finishedByPerformerIdAndType =
                dao.findFinishedByPerformerIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, finishedByPerformerIdAndType.size());
    }

    @Test
    public void findFinishedByPatientIdAndType() throws Exception {
        List<TherapyDTO> finishedByPatientIdAndType =
                dao.findFinishedByPatientIdAndType(8, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(1, finishedByPatientIdAndType.size());
    }

    @Test
    public void findFutureByPerformerIdAndType() throws Exception {
        List<TherapyDTO> futureByPerformerIdAndType =
                dao.findFutureByPerformerIdAndType(57, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByPerformerIdAndType.size());
    }

    @Test
    public void findFutureByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findFutureByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByPatientIdAndType.size());
    }

    @Test
    public void findByPerformerIdAndType() throws Exception {
        List<TherapyDTO> futureByMedicIdAndType =
                dao.findByPerformerIdAndType(69, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(2, futureByMedicIdAndType.size());
    }

    @Test
    public void findByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(3, futureByPatientIdAndType.size());
    }

    @Test
    public void findCurrentByPerformerIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findCurrentByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByPerformerIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByPerformerIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test
    public void findCurrentByPatientIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findCurrentByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByPatientIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByPatientIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findFinishedByPerformerIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFinishedByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByPerformerIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByPerformerIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findFinishedByPatientIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFinishedByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByPatientIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findFinishedByPatientIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findFutureByPerformerIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFutureByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFutureByPerformerIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findFutureByPerformerIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }

    @Test
    public void findFutureByPatientIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFutureByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFutureByPatientIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFutureByPatientIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }

    @Test
    public void findByPerformerIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByPerformerIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByPerformerIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByPerformerIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }

    @Test
    public void findByPatientIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByPatientIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByPatirntIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }
}