package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.PatientQueryExecutor;
import domain.Patient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientJdbcDAOTest {
    private PatientJdbcDAO jdbcDao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private PatientQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new PatientJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;
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

    @Test
    public void findByDepartmentIdTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDepartmentIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionManagerMock).rollbackTransaction();
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

    @Test
    public void findByDoctorIdTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByDoctorId(100L);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDoctorIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDoctorId(100L);
        verify(connectionManagerMock).rollbackTransaction();
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

    @Test
    public void findByStateIdTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByState(Patient.State.ADDMITTED);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByStateTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByState(Patient.State.ADDMITTED);
        verify(connectionManagerMock).rollbackTransaction();
    }
}