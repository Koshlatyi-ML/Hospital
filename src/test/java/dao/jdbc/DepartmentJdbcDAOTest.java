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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertArrayEquals;
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
    private ConnectionManager realConnManag;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcDaoMock = new DepartmentJdbcDAO(queryExecutorMock, connectionManagerMock);
        when(connectionManagerMock.getConnection()).thenReturn(connectionMock);
        jdbcDaoMock.connectionManager = connectionManagerMock;

        when(realConnManag.getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection())
                .thenReturn(TestingConnectionFactory.getInstance().getConnection());

        DepartmentQueryExecutor departmentQueryExecutor =
                QueryExecutorFactory.getInstance().getDepartmentQueryExecutor();

        dao = new DepartmentJdbcDAO(departmentQueryExecutor, realConnManag);
    }

    @Test
    public void find() throws Exception {
        DepartmentDTO tested = dao.find(95).orElseThrow(Exception::new);
        DepartmentDTO desired = new DepartmentDTO.Builder()
                .setId(95)
                .setName("department1")
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        DepartmentDTO[] departmentDTOS = {
                new DepartmentDTO.Builder()
                        .setId(95)
                        .setName("department1")
                        .build()
                ,
                new DepartmentDTO.Builder()
                        .setId(96)
                        .setName("department2")
                        .build(),
                new DepartmentDTO.Builder()
                        .setId(97)
                        .setName("department3")
                        .build(),
                new DepartmentDTO.Builder()
                        .setId(98)
                        .setName("department4")
                        .build()
        };
        assertArrayEquals(departmentDTOS, dao.findAll().toArray());
    }

    @Test
    public void create() throws Exception {
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setName("New")
                .build();

        dao.create(dto);

        DepartmentDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setName("New")
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<DepartmentDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        DepartmentDTO tested = new DepartmentDTO.Builder()
                .setName("name1")
                .build();
        dao.create(tested);

        tested.setName("name2");
        dao.update(tested);
        DepartmentDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }


    @Test
    public void findByNameNonTransactionalCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        jdbcDaoMock.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test
    public void findByNameWxisting() throws Exception {
        String name = "department1";

        DepartmentDTO tested = dao.findByName(name).orElseThrow(Exception::new);

        DepartmentDTO desired = new DepartmentDTO.Builder()
                .setId(tested.getId())
                .setName(name)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findByNameNonExisting() throws Exception {
        assertFalse(dao.findByName("sfsdfvs").isPresent());
    }

    @Test(expected = RuntimeException.class)
    public void findByNameNonTransactionalSqlExceptionCloseConnection() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(false);
        when(jdbcDaoMock.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDaoMock.findByName("Misha");
        verify(connectionMock).close();
    }

    @Test(expected = RuntimeException.class)
    public void findByNameTransactionalSqlExceptionRollback() throws Exception {
        when(connectionManagerMock.isTransactional()).thenReturn(true);
        when(jdbcDaoMock.getQueryExecutor()).thenThrow(SQLException.class);
        jdbcDaoMock.findByName("Misha");
        verify(connectionManagerMock).tryRollback();
    }
}