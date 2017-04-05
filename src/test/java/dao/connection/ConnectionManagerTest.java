package dao.connection;

import dao.connection.exception.IllegalTransactionStateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConnectionManagerTest {

    @Mock
    private ConnectionFactory mockConnectionFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestingConnectionFactory connectionFactory = TestingConnectionFactory.getInstance();
        when(mockConnectionFactory.getConnection())
                .thenReturn(connectionFactory.getConnection())
                .thenReturn(connectionFactory.getConnection())
                .thenReturn(connectionFactory.getConnection());
    }

    @Test
    public void getConnectionNotNull() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        assertNotNull(connectionManager.getConnection());
    }

    @Test
    public void beginTransactionNoParameters() throws Exception {
        ConnectionManager connectionManagerSpy = spy(new ConnectionManager(mockConnectionFactory));
        connectionManagerSpy.beginTransaction();

        verify(connectionManagerSpy).beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    @Test
    public void beginTransactionScopedConnection() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);

        assertSame(connectionManager.getConnection(), connectionManager.getConnection());
    }

    @Test
    public void beginTransactionSetAutoCommitFalse() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);

        assertFalse(connectionManager.getConnection().getAutoCommit());
    }

    @Test
    public void beginTransactionSetTransactionIsolationLevel() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;
        connectionManager.beginTransaction(isolationLevel);

        assertEquals(isolationLevel, connectionManager.getConnection().getTransactionIsolation());
    }


    @Test
    public void beginTransactionSetAutoCommitException() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).setAutoCommit(false);


        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
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

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
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

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        try {
            connectionManager.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void finishTransactionNotScopedConnections() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();

        assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
    }

    @Test
    public void finishTransactionCommit() throws Exception {
        Connection mock = mock(Connection.class);
        when(mockConnectionFactory.getConnection())
                .thenReturn(mock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();

        verify(mock).commit();
    }

    @Test
    public void finishTransactionOnCommitExceptionRollbackCommit() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).commit();

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        try {
            connectionManager.finishTransaction();
        } catch (RuntimeException e) {
            verify(throwerMock).rollback();
        }
    }

    @Test
    public void finishTransactionOnCommitExceptionNotScopedConnection() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).commit();

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
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

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
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

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
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

        when(mockConnectionFactory.getConnection())
                .thenReturn(observedMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.rollbackTransaction();

        verify(observedMock).rollback();
    }

    @Test
    public void rollbackTransactionNotScopedConnection() throws Exception {
        when(mockConnectionFactory.getConnection())
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        try {
            connectionManager.rollbackTransaction();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void rollbackTransactionRollbackExceptionNotScopedConnection() throws Exception {
        Connection throwerMock = mock(Connection.class);
        Mockito.doThrow(SQLException.class).when(throwerMock).rollback();

        when(mockConnectionFactory.getConnection())
                .thenReturn(throwerMock)
                .thenReturn(mock(Connection.class))
                .thenReturn(mock(Connection.class));

        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        try {
            connectionManager.rollbackTransaction();
        } catch (RuntimeException e) {
            assertNotSame(connectionManager.getConnection(), connectionManager.getConnection());
        }
    }

    @Test
    public void isTransactionFalseNotOpened() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        assertFalse(connectionManager.isTransactional());
    }

    @Test
    public void isTransactionalFalseRollbacked() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.rollbackTransaction();
        assertFalse(connectionManager.isTransactional());
    }

    @Test
    public void isTransactionalFalseFinished() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.finishTransaction();
        assertFalse(connectionManager.isTransactional());
    }

    @Test
    public void isTransactionalFalseTrue() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        assertTrue(connectionManager.isTransactional());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void IllegalTransactionalMultiplyBegin() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.beginTransaction();
        connectionManager.beginTransaction();
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void IllegalTransactionalRollbackWithoutBegin() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.rollbackTransaction();
        assertTrue(connectionManager.isTransactional());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void IllegalTransactionalFinishWithoutBegin() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager(mockConnectionFactory);
        connectionManager.finishTransaction();
        assertTrue(connectionManager.isTransactional());
    }
}