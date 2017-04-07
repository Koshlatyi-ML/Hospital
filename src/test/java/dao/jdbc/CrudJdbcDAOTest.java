package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.QueryExecutor;
import domain.dto.AbstractDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CrudJdbcDAOTest {
    @Spy
    private CrudJdbcDAO crudJdbcDaoSpy;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private QueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenReturn(queryExecutorMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        crudJdbcDaoSpy.connectionManager = connectionManagerMock;

    }

    @Test
    public void findNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        crudJdbcDaoSpy.find(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.find(100L);
        verify(connectionMock).close();
    }

    @Test
    public void findTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        crudJdbcDaoSpy.find(100L);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.find(100L);
        verify(connectionManagerMock).rollbackTransaction();
    }

    @Test
    public void findAllNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        crudJdbcDaoSpy.findAll();
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findAllNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.findAll();
        verify(connectionMock).close();
    }

    @Test
    public void findAllTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        crudJdbcDaoSpy.findAll();
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findAllTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.findAll();
        verify(connectionManagerMock).rollbackTransaction();
    }

    @Test
    public void createNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        crudJdbcDaoSpy.create(mock(AbstractDTO.class));
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void createNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.create(mock(AbstractDTO.class));
        verify(connectionMock).close();
    }

    @Test
    public void createTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        crudJdbcDaoSpy.create(mock(AbstractDTO.class));
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void createTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.create(mock(AbstractDTO.class));
        verify(connectionManagerMock).rollbackTransaction();
    }

    @Test
    public void updateNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        crudJdbcDaoSpy.update(mock(AbstractDTO.class));
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void updateNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.update(mock(AbstractDTO.class));
        verify(connectionMock).close();
    }

    @Test
    public void updateTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        crudJdbcDaoSpy.update(mock(AbstractDTO.class));
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void updateTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.update(mock(AbstractDTO.class));
        verify(connectionManagerMock).rollbackTransaction();
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void deleteNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        crudJdbcDaoSpy.delete(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void deleteNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.delete(100L);
        verify(connectionMock).close();
    }

    @Test
    public void deleteTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        crudJdbcDaoSpy.delete(100L);
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void deleteTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(crudJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        crudJdbcDaoSpy.delete(100L);
        verify(connectionManagerMock).rollbackTransaction();
    }

    @Test
    public void deleteObjectRedirect() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        AbstractDTO dtoMock = mock(AbstractDTO.class);
        crudJdbcDaoSpy.delete(dtoMock);
        verify(crudJdbcDaoSpy).delete(dtoMock.getId());
    }
}