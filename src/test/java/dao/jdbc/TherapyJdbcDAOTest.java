package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.dto.TherapyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TherapyJdbcDAOTest {
    private TherapyJdbcDAO jdbcDao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private TherapyQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new TherapyJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;
    }

    @Test
    public void findCurrentByDoctorIdAndTypeNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findCurrentByDoctorIdAndTypeNonTransactionalSqlExceptionCloseConnection()
            throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock).close();
    }

    @Test
    public void findCurrentByDoctorIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findCurrentByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findCurrentByMedicIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findCurrentByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findCurrentByPatientIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findCurrentByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFinishedByDoctorIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFinishedByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFinishedByMedicIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFinishedByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFinishedByPatientIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFinishedByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFutureByDoctorIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFutureByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFutureByMedicIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFutureByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findFutureByPatientIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findFutureByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findByDoctorIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByDoctorIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findByMedicIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByMedicIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
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

    @Test
    public void findByPatientIdAndTypeTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByPatirntIdAndTypeTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByPatientIdAndType(100L, TherapyDTO.Type.SURGERY_OPERATION);
    }
}