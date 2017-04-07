package dao.jdbc;

import dao.DepartmentDAO;
import dao.connection.ConnectionManager;
import dao.connection.TestingConnectionFactory;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.dto.DepartmentDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepartmentJdbcDAOTest {
    private DepartmentJdbcDAO jdbcDaoMock;
    private DepartmentDAO dao;
    @Mock
    private ConnectionManager connectionManagerMock;
    @Mock
    private DepartmentQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ConnectionManager connectionManager2Mock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDaoMock = new DepartmentJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDaoMock.connectionManager = connectionManagerMock;

        when(connectionManager2Mock.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());
        DepartmentQueryExecutor departmentQueryExecutor =
                QueryExecutorFactory.getInstance().getDepartmentQueryExecutor();


        dao = new DepartmentJdbcDAO(departmentQueryExecutor, connectionManager2Mock);
    }

    @Test
    public void findByNameNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDaoMock.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test
    public void findByName() throws Exception {
        Optional<DepartmentDTO> dto = dao.findByName("department1");

    }

    @Test(expected = RuntimeException.class)
    public void findByNameNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDaoMock.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDaoMock.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test
    public void findByNameTransactionalNotCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        jdbcDaoMock.findByName("Misha");
        verify(connectionMock, Mockito.never()).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByNameTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDaoMock.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDaoMock.findByName("Misha");
        verify(connectionManagerMock).tryRollback();
    }
}