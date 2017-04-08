package dao.jdbc;

import dao.connection.ConnectionManager;
import dao.jdbc.query.StuffQueryExecutor;
import domain.dto.AbstractStuffDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StuffJdbcDAOTest {
    @Spy
    private StuffJdbcDAO stuffJdbcDaoSpy;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private StuffQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenReturn(queryExecutorMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        stuffJdbcDaoSpy.connectionManager = connectionManagerMock;
    }

    @Test
    public void createNonTransactionalDoTransaction() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        AbstractStuffDTO dtoMock = mock(AbstractStuffDTO.class);
        stuffJdbcDaoSpy.create(dtoMock);
        InOrder inOrder = Mockito.inOrder(connectionManagerMock, queryExecutorMock);
        inOrder.verify(connectionManagerMock).beginTransaction();
        inOrder.verify(queryExecutorMock).queryInsert(connectionMock, dtoMock);
        inOrder.verify(connectionManagerMock).finishTransaction();
    }

    @Test(expected = RuntimeException.class)
    public void createNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.create(mock(AbstractStuffDTO.class));
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void createTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.create(mock(AbstractStuffDTO.class));
        verify(connectionManagerMock).tryRollback();
    }

    @Test
    public void updateNonTransactionalDoTransaction() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        AbstractStuffDTO dtoMock = mock(AbstractStuffDTO.class);
        stuffJdbcDaoSpy.update(dtoMock);
        InOrder inOrder = Mockito.inOrder(connectionManagerMock, queryExecutorMock);
        inOrder.verify(connectionManagerMock).beginTransaction();
        inOrder.verify(queryExecutorMock).queryUpdate(connectionMock, dtoMock);
        inOrder.verify(connectionManagerMock).finishTransaction();
    }

    @Test(expected = RuntimeException.class)
    public void updateNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.update(mock(AbstractStuffDTO.class));
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void updateTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.update(mock(AbstractStuffDTO.class));
        verify(connectionManagerMock).tryRollback();
    }

    @Test(expected = RuntimeException.class)
    public void deleteNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.delete(mock(AbstractStuffDTO.class));
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void deleteTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.delete(mock(AbstractStuffDTO.class));
        verify(connectionManagerMock).tryRollback();
    }

    @Test(expected = RuntimeException.class)
    public void findByDepartmentIdNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.findByDepartmentId(100L);
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByDepartmentIdTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(stuffJdbcDaoSpy.getQueryExecutor()).thenThrow(SQLException.class);
        stuffJdbcDaoSpy.findByDepartmentId(100L);
        verify(connectionManagerMock).tryRollback();
    }
}