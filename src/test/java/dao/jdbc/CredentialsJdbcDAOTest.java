package dao.jdbc;

import dao.connection.TestConnectionProvider;
import dao.jdbc.query.CredentialsQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.dto.CredentialsDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CredentialsJdbcDAOTest {
    private CredentialsJdbcDAO jdbcDao;
    private CredentialsJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private CredentialsQueryExecutor queryExecutorMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());

        jdbcDao = new CredentialsJdbcDAO(queryExecutorMock, connectionManager);

        CredentialsQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getCredentialsQueryExecutor();

        dao = new CredentialsJdbcDAO(queryExecutor, connectionManager);
    }

    @Test
    public void find() throws Exception {
        CredentialsDTO tested = dao.find(7).orElseThrow(Exception::new);

        CredentialsDTO desired = new CredentialsDTO.Builder()
                .setId(7)
                .setLogin("Nikolay")
                .setPassword("password")
                .setRole(1)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<CredentialsDTO> all = dao.findAll();
        assertEquals(21, all.size());
    }

    @Test
    public void create() throws Exception {
        CredentialsDTO dto = new CredentialsDTO.Builder()
                .setLogin("test")
                .setPassword("test1111")
                .setRole(2)
                .build();

        dao.create(dto);

        CredentialsDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        CredentialsDTO dto = new CredentialsDTO.Builder()
                .setLogin("test")
                .setPassword("test1111")
                .setRole(2)
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<CredentialsDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        CredentialsDTO tested = new CredentialsDTO.Builder()
                .setLogin("test")
                .setPassword("test1111")
                .setRole(2)
                .build();
        dao.create(tested);

        tested.setLogin("name2");
        tested.setPassword("azaaz");
        tested.setRole(1);

        dao.update(tested);
        CredentialsDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }

    @Test
    public void findByLoginAndPassword() throws Exception {
        CredentialsDTO tested = dao.findByLoginAndPassword("Nikolay", "password")
                .orElseThrow(Exception::new);

        CredentialsDTO desired = new CredentialsDTO.Builder()
                .setId(7)
                .setLogin("Nikolay")
                .setPassword("password")
                .setRole(1)
                .build();
        assertEquals(desired, tested);
    }
}