package dao.jdbc;

import dao.connection.TestConnectionProvider;
import dao.jdbc.query.QueryExecutorFactory;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.TherapyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.when;

public class TherapyJdbcDAOTest {
    private TherapyJdbcDAO dao;
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

        TherapyQueryExecutor queryExecutor =
                QueryExecutorFactory.getInstance().getTherapyQueryExecutor();

        dao = new TherapyJdbcDAO(queryExecutor, connectionManager);
    }

    @Test
    public void find() throws Exception {
        long id = 11;
        TherapyDTO tested = dao.find(id).orElseThrow(Exception::new);

        TherapyDTO desired = new TherapyDTO.Builder()
                .setId(id)
                .setTitle("Title1")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("Description1")
                .setAppointmentDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2017, 4, 15, 16, 55, 0)))
                .setCompleteDateTime(
                        Timestamp.valueOf(LocalDateTime.of(2017, 4, 16, 16, 0, 0)))
                .setPatientId(6)
                .setPerformerId(55)
                .build();
        assertEquals(desired, tested);
    }

    @Test
    public void findAll() throws Exception {
        List<TherapyDTO> all = dao.findAll();
        assertEquals(8, all.size());
    }

    @Test
    public void create() throws Exception {
        Timestamp appTimestamp = Timestamp.from(Instant.now());
        Timestamp complTimestamp = Timestamp.from((Instant.now().plus(1, ChronoUnit.DAYS)));


        TherapyDTO dto = new TherapyDTO.Builder()
                .setTitle("sdfsf")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("sdsgsg")
                .setAppointmentDateTime(appTimestamp)
                .setCompleteDateTime(complTimestamp)
                .setPatientId(6)
                .setPerformerId(55)
                .build();

        dao.create(dto);

        TherapyDTO tested = dao.find(dto.getId()).orElseThrow(Exception::new);
        dao.delete(dto);
        assertEquals(dto, tested);
    }

    @Test
    public void delete() throws Exception {
        Timestamp appTimestamp = Timestamp.from(Instant.now());
        Timestamp complTimestamp = Timestamp.from((Instant.now().plus(1, ChronoUnit.DAYS)));

        TherapyDTO dto = new TherapyDTO.Builder()
                .setTitle("sdfsf")
                .setType(TherapyDTO.Type.SURGERY_OPERATION)
                .setDescription("sdsgsg")
                .setAppointmentDateTime(appTimestamp)
                .setCompleteDateTime(complTimestamp)
                .setPatientId(6)
                .setPerformerId(55)
                .build();


        dao.create(dto);
        dao.delete(dto);
        Optional<TherapyDTO> tested = dao.find(dto.getId());

        assertFalse(tested.isPresent());
    }

    @Test
    public void findCurrentByPerformerIdAndType() throws Exception {
        List<TherapyDTO> currentByPerformerIdAndType =
                dao.findCurrentByPerformerIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByPerformerIdAndType.size());
    }

    @Test
    public void findCurrentByPatientIdAndType() throws Exception {
        List<TherapyDTO> currentByPatientIdAndType =
                dao.findCurrentByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, currentByPatientIdAndType.size());
    }

    @Test
    public void findFinishedByPerformerIdAndType() throws Exception {
        List<TherapyDTO> finishedByPerformerIdAndType =
                dao.findFinishedByPerformerIdAndType(55, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, finishedByPerformerIdAndType.size());
    }

    @Test
    public void findFinishedByPatientIdAndType() throws Exception {
        List<TherapyDTO> finishedByPatientIdAndType =
                dao.findFinishedByPatientIdAndType(8, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(1, finishedByPatientIdAndType.size());
    }

    @Test
    public void findFutureByPerformerIdAndType() throws Exception {
        List<TherapyDTO> futureByPerformerIdAndType =
                dao.findFutureByPerformerIdAndType(57, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByPerformerIdAndType.size());
    }

    @Test
    public void findFutureByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findFutureByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(1, futureByPatientIdAndType.size());
    }

    @Test
    public void findByPerformerIdAndType() throws Exception {
        List<TherapyDTO> futureByMedicIdAndType =
                dao.findByPerformerIdAndType(69, TherapyDTO.Type.PHYSIOTHERAPY);
        assertEquals(2, futureByMedicIdAndType.size());
    }

    @Test
    public void findByPatientIdAndType() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findByPatientIdAndType(6, TherapyDTO.Type.SURGERY_OPERATION);
        assertEquals(3, futureByPatientIdAndType.size());
    }

    @Test
    public void findFinishedByPatientId() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findFinishedByPatientId(6);
        assertEquals(1, futureByPatientIdAndType.size());
    }

    @Test
    public void findNotFinishedByPatientId() throws Exception {
        List<TherapyDTO> futureByPatientIdAndType =
                dao.findNotFinishedByPatientId(6);
        assertEquals(3, futureByPatientIdAndType.size());
    }
}