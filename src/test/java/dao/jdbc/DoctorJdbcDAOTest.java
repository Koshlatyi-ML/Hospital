package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.DoctorQueryExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorJdbcDAOTest {
    private DoctorJdbcDAO jdbcDao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private DoctorQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new DoctorJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;
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

    @Test
    public void findByPatientIdTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByPatientIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByDepartmentId(100L);
        verify(connectionManagerMock).tryRollback();
    }


}