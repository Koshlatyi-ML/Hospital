package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import domain.dto.DepartmentDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepartmentJdbcDAOTest {
    private DepartmentJdbcDAO jdbcDao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private DepartmentQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDao = new DepartmentJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDao.connectionManager = connectionManagerMock;
    }

    @Test
    public void findByNameNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDao.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByNameNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test
    public void findByNameTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDao.findByName("Misha");
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByNameTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDao.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDao.findByName("Misha");
        verify(connectionManagerMock).rollbackTransaction();
    }
}