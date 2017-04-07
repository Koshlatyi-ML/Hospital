package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.PersonQueryExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonJdbcDAOTest {
    @Spy
    private PersonJdbcDAO personJdbcDaoSpy;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private PersonQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(personJdbcDaoSpy.getQueryExecutor()).thenReturn(queryExecutorMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        personJdbcDaoSpy.connectionManager = connectionManagerMock;
    }

    @Test
    public void findByFullNameNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        personJdbcDaoSpy.findByFullName("Misha");
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByFullNameNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(personJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        personJdbcDaoSpy.findByFullName("Misha");
        verify(connectionMock).close();
    }

    @Test
    public void findByFullNameTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        personJdbcDaoSpy.findByFullName("Misha");
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByFullNameTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(personJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        personJdbcDaoSpy.findByFullName("Misha");
        verify(connectionManagerMock).tryRollback();
    }
}