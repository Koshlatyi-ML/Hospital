package dao.jdbc;

import dao.connection.TestConnectionProvider;
import dao.jdbc.query.CredentialsQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.CredentialsDTO;
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

    private CredentialsJdbcDAO dao;

    @Mock
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());


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
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void count() throws Exception {
        assertEquals(23, dao.count());
    }

    @Test
    public void findAll() throws Exception {
        List<CredentialsDTO> all = dao.findAll(10, 5);
        assertEquals(5, all.size());
    }

    @Test
    public void create() throws Exception {
        CredentialsDTO dto = new CredentialsDTO.Builder()
                .setLogin("test")
                .setPassword("test1111")
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
                .build();
        dao.create(tested);

        tested.setLogin("name2");
        tested.setPassword("azaaz");

        dao.update(tested);
        CredentialsDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }
}