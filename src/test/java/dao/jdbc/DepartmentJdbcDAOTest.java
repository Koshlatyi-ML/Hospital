package dao.jdbc;

import dao.DepartmentDAO;
import dao.connection.TestConnectionProvider;
import dao.jdbc.query.DepartmentQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.DepartmentDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

public class DepartmentJdbcDAOTest {
    private DepartmentJdbcDAO jdbcDaoMock;
    private DepartmentDAO dao;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private DepartmentQueryExecutor queryExecutorMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());

        jdbcDaoMock = new DepartmentJdbcDAO(queryExecutorMock, connectionManager);

        DepartmentQueryExecutor departmentQueryExecutor =
                QueryExecutorFactory.getInstance().getDepartmentQueryExecutor();

        dao = new DepartmentJdbcDAO(departmentQueryExecutor, connectionManager);
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
    public void findByNameExisting() throws Exception {
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
}