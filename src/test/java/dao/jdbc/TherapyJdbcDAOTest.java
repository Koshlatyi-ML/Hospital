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
    public void findCurrentByDoctorIdAndType() throws Exception {
        List<TherapyDTO> currentByDoctorIdAndType =
                dao.findCurrentByDoctorIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByDoctorIdAndType.size());
    }

    @Test
    public void findCurrentByMedicIdAndType() throws Exception {
        List<TherapyDTO> currentByMedicIdAndType =
                dao.findCurrentByMedicIdAndType(68, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(1, currentByMedicIdAndType.size());
    }

    @Test
    public void findCurrentByPatientIdAndType() throws Exception {
        List<TherapyDTO> currentByPatientIdAndType =
                dao.findCurrentByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByPatientIdAndType.size());
    }

    @Test
    public void findFinishedByDoctorIdAndType() throws Exception {
        List<TherapyDTO> finishedByDoctorIdAndType =
                dao.findFinishedByDoctorIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, finishedByDoctorIdAndType.size());
    }

    @Test
    public void findFinishedByMedicIdAndType() throws Exception {
        List<TherapyDTO> finishedByMedicIdAndType =
                dao.findFinishedByMedicIdAndType(68, TherapyDTO.Type.PHARMACOTHERAPY);
        assertEquals(1, finishedByMedicIdAndType.size());
    }

    @Test
    public void findFinishedByPatientIdAndType() throws Exception {
        List<TherapyDTO> finishedByPatientIdAndType =
                dao.findFinishedByPatientIdAndType(8, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(1, finishedByPatientIdAndType.size());
    }

    @Test
    public void findFutureByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findFutureByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByPatientIdAndType.size());
    }

    @Test
    public void findFutureByDoctorIdAndType() throws Exception {
        List<TherapyDTO> futureByDoctorIdAndType =
                dao.findFutureByDoctorIdAndType(57, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByDoctorIdAndType.size());
    }

    @Test
    public void findFutureByMedicIdAndType() throws Exception {
        List<TherapyDTO> futureByMedicIdAndType =
                dao.findFutureByMedicIdAndType(69, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(1, futureByMedicIdAndType.size());
    }


    @Test
    public void findCurrentByDoctorIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test
    public void findByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(3, futureByPatientIdAndType.size());
    }

    @Test
    public void findByDoctorIdAndType() throws Exception {
        List<TherapyDTO> futureByDoctorIdAndType =
                dao.findByDoctorIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(2, futureByDoctorIdAndType.size());
    }

    @Test
    public void findByMedicIdAndType() throws Exception {
        List<TherapyDTO> futureByMedicIdAndType =
                dao.findByMedicIdAndType(69, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(2, futureByMedicIdAndType.size());
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByDoctorIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByDoctorIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findCurrentByMedicIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findCurrentByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByMedicIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByMedicIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
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
    public void findFinishedByDoctorIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFinishedByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByDoctorIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByDoctorIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void findFinishedByMedicIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFinishedByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFinishedByMedicIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findFinishedByMedicIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFinishedByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
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
    public void findFutureByDoctorIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFutureByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFutureByDoctorIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findFutureByDoctorIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }

    @Test
    public void findFutureByMedicIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findFutureByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findFutureByMedicIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findFutureByMedicIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findFutureByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
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
    public void findByDoctorIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDoctorIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByDoctorIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }

    @Test
    public void findByMedicIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByMedicIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }


    @Test(expected = RuntimeException.class)
    public void findByMedicIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
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