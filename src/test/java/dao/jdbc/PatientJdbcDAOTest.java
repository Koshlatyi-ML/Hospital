package dao.jdbc;

import dao.connection.TestConnectionProvider;
import dao.jdbc.query.PatientQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import domain.Patient;
import domain.dto.PatientDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.when;

public class PatientJdbcDAOTest {
    private PatientJdbcDAO jdbcDao;
    private PatientJdbcDAO dao;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private PatientQueryExecutor queryExecutorMock;
    @Mock
    private Connection connectionMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestConnectionProvider connectionProvider = TestConnectionProvider.getInstance();
        when(connectionManager.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection())
                .thenReturn(connectionProvider.getConnection());

        jdbcDao = new PatientJdbcDAO(queryExecutorMock, connectionManager);

        PatientQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getPatientQueryExecutor();

        dao = new PatientJdbcDAO(queryExecutor, connectionManager);
    }

    @Test
    public void find() throws Exception {
        long id = 7;
        PatientDTO tested = dao.find(id).orElseThrow(Exception::new);

        PatientDTO desired = new PatientDTO.Builder()
                .setId(id)
                .setName("PatientName2")
                .setSurname("PatientSurname2")
                .setDoctorId(54)
                .setCompliants("Complaints2")
                .setDiagnosis(null)
                .setState(PatientDTO.State.ADDMITTED)
                .setCredentialsId(18)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<PatientDTO> all = dao.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void create() throws Exception {
        PatientDTO dto = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .setCredentialsId(21)
                .build();

        dao.create(dto);

        PatientDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        PatientDTO dto = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .setCredentialsId(21)
                .build();

        dao.create(dto);
        dao.delete(dto);
        Optional<PatientDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void update() throws Exception {
        PatientDTO tested = new PatientDTO.Builder()
                .setName("aaa")
                .setSurname("bbb")
                .setDoctorId(56)
                .setCompliants("ccc")
                .setDiagnosis(null)
                .setState(PatientDTO.State.APPLYIED)
                .setCredentialsId(21)
                .build();
        dao.create(tested);

        tested.setName("name2");
        tested.setSurname("azaaz");
        tested.setDoctorId(55);
        tested.setDiagnosis("volc4ank@");

        dao.update(tested);
        PatientDTO updated = dao.find(tested.getId()).orElseThrow(Exception::new);
        assertEquals(tested, updated);

        dao.delete(tested.getId());
    }

    @Test
    public void findByCredentialsId() throws Exception {
        PatientDTO tested = dao.findByCredentialsId(18).orElseThrow(Exception::new);

        PatientDTO desired = new PatientDTO.Builder()
                .setId(tested.getId())
                .setName("PatientName2")
                .setSurname("PatientSurname2")
                .setDoctorId(54)
                .setCompliants("Complaints2")
                .setDiagnosis(null)
                .setState(PatientDTO.State.ADDMITTED)
                .setCredentialsId(18)
                .build();

        assertEquals(desired, tested);
    }

    @Test
    public void findByDepartmentId() throws Exception {
        List<PatientDTO> byDepartmentId = dao.findByDepartmentId(95);
        assertEquals(2, byDepartmentId.size());
    }

    @Test
    public void findByDoctorId() throws Exception {
        List<PatientDTO> byDoctorId = dao.findByDoctorId(54);
        assertEquals(2, byDoctorId.size());
    }

    @Test
    public void findByState() throws Exception {
        List<PatientDTO> daoByState = dao.findByState(Patient.State.DISCHARGED);
        assertEquals(1, daoByState.size());
    }
}