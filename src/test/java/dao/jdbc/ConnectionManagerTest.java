package dao.jdbc;

import dao.connection.ConnectionProvider;
import dao.connection.TestConnectionProvider;
import dao.exception.IllegalTransactionStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.mock;

public class ConnectionManagerTest {

    private ConnectionManager connectionManager;
    @Mock
    private ConnectionProvider connectionProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        connectionManager = new ConnectionManager(connectionProvider);
        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());
    }

    @Test
    public void getConnectionNotNull() throws Exception {
        assertNotNull(connectionManager.getConnection());
    }

    @Test
    public void beginTransactionNoParameters() throws Exception {
        ConnectionManager connectionManagerSpy = spy(connectionManager);
        connectionManagerSpy.beginTransaction();

        verify(connectionManagerSpy).beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    @Test
    public void beginTransactionScopedConnection() throws Exception {
        connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);

        assertSame(connectionManager.getConnection(), connectionManager.getConnection());
    }

    @Test
    public void beginTransactionSetAutoCommitFalse() throws Exception {
        connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);

        assertFalse(connectionManager.getConnection().getAutoCommit());
    }

    @Test
    public void beginTransactionSetTransactionIsolationLevel() throws Exception {
        int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;
        connectionManager.beginTransaction(isolationLevel);

        assertEquals(isolationLevel, connectionManager.getConnection().getTransactionIsolation());
    }

    @Test
    public void beginTransactionSetAutoCommitException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).setAutoCommit(false);

        try {
            connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }

    }

    @Test
    public void beginTransactionSetTransactionIsolationException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock)
                .setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        try {
            connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void beginTransactionOnInnerOnCloseException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).setAutoCommit(false);
        Mockito.doThrow(SQLException.class).when(throwerMock).close();

        try {
            connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void finishNestedTransactionScopedConnections() throws Exception {
        connectionManager.beginTransaction();
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();

        assertSame(connectionManager.getConnection(), connectionManager.getConnection());
    }

    @Test
    public void finishWithNestedTransactionNotScopedConnections() throws Exception {
        connectionManager.beginTransaction();
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();
        connectionManager.finishTransaction();

        assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
    }


    @Test
    public void finishTransactionNotScopedConnections() throws Exception {
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();

        assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
    }

    @Test
    public void finishTransactionCommit() throws Exception {
        Connection mock = mock(Connection.class);

        when(connectionManager.getConnection()).thenReturn(mock);

        connectionManager.beginTransaction();
        connectionManager.finishTransaction();

        verify(mock).commit();
    }

    @Test
    public void finishTransactionOnCommitExceptionNotScopedConnection() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).commit();

        connectionManager.beginTransaction();
        try {
            connectionManager.finishTransaction();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void finishTransactionOnCloseException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).close();

        connectionManager.beginTransaction();
        try {
            connectionManager.finishTransaction();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void finishTransactionOnRollbackException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).rollback(null);

        connectionManager.beginTransaction();
        try {
            connectionManager.finishTransaction();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void rollbackTransaction() throws Exception {
        Connection observedMock = mock(Connection.class);
        when(connectionManager.getConnection()).thenReturn(observedMock);

        connectionManager.beginTransaction();
        connectionManager.tryRollback();

        verify(observedMock).rollback();
    }

    @Test
    public void rollbackTransactionNotScopedConnection() throws Exception {
        connectionManager.beginTransaction();
        try {
            connectionManager.tryRollback();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void rollbackTransactionRollbackExceptionNotScopedConnection() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).rollback();

        connectionManager.beginTransaction();
        try {
            connectionManager.tryRollback();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void isTransactionFalseNotOpened() throws Exception {
        assertTrue(connectionManager.getConnection().getAutoCommit());
    }

    @Test
    public void isTransactionalFalseRollbacked() throws Exception {
        connectionManager.beginTransaction();
        connectionManager.tryRollback();
        assertTrue(connectionManager.getConnection().getAutoCommit());
    }

    @Test
    public void isTransactionalFalseFinished() throws Exception {
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();
        assertTrue(connectionManager.getConnection().getAutoCommit());
    }

    @Test
    public void isTransactionalFalseTrue() throws Exception {
        connectionManager.beginTransaction();
        assertFalse(connectionManager.getConnection().getAutoCommit());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void IllegalTransactionalFinishWithoutBegin() throws Exception {
        connectionManager.finishTransaction();
        assertTrue(connectionManager.getConnection().getAutoCommit());
    }

}
