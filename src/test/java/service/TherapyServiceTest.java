package service;

import dao.DaoManager;
import dao.jdbc.TestJdbcDaoManager;
import domain.TherapyDTO;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TherapyServiceTest {

    @Test
    public void getSize() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService service = new TherapyService(daoManagerMock);
        assertEquals(8, service.getSize());
    }

    @Test
    public void getAll() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService service = new TherapyService(daoManagerMock);
        assertEquals(5, service.getAll(0, 5).size());
    }

    @Test
    public void get() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService therapyService = new TherapyService(daoManagerMock);

        TherapyDTO dto =
                new TherapyDTO.Builder()
                        .setId(11)
                        .setTitle("Title1")
                        .setType(TherapyDTO.Type.SURGERY_OPERATION)
                        .setDescription("Description1")
                        .setAppointmentDateTime(Timestamp.valueOf(LocalDateTime.of(2017, 4, 15, 16, 55, 0)))
                        .setCompleteDateTime(Timestamp.valueOf(LocalDateTime.of(2017, 4, 16, 16, 0, 0)))
                        .setPatientId(6)
                        .setPerformerId(55)
                        .build();
        assertEquals(dto, therapyService.get(11).get());
    }

    @Test
    public void getEmpty() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService service = new TherapyService(daoManagerMock);
        assertFalse(service.get(-1).isPresent());
    }

    @Test
    public void create() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService therapyService = new TherapyService(daoManagerMock);
        TherapyDTO dto =
                new TherapyDTO.Builder()
                        .setTitle("Title1")
                        .setType(TherapyDTO.Type.SURGERY_OPERATION)
                        .setDescription("Description1")
                        .setAppointmentDateTime(Timestamp.valueOf(LocalDateTime.of(3027, 5, 15, 16, 55, 0)))
                        .setCompleteDateTime(Timestamp.valueOf(LocalDateTime.of(3027, 5, 16, 16, 0, 0)))
                        .setPatientId(6)
                        .setPerformerId(55)
                        .build();
        therapyService.create(dto);
        assertEquals(dto, therapyService.get(dto.getId()).get());
        therapyService.delete(dto.getId());
    }

    @Test
    public void update() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService service = new TherapyService(daoManagerMock);
        String description  = "testedDescription";
        String title = "testedTitle";

        TherapyDTO therapyDTO = service.get(11).get();
        String oldTitle = therapyDTO.getTitle();
        String oldDescription = therapyDTO.getDescription();

        therapyDTO.setDescription(description);
        therapyDTO.setTitle(title);
        service.update(therapyDTO);

        TherapyDTO dto =
                new TherapyDTO.Builder()
                        .setId(therapyDTO.getId())
                        .setTitle(title)
                        .setType(TherapyDTO.Type.SURGERY_OPERATION)
                        .setDescription(description)
                        .setAppointmentDateTime(Timestamp.valueOf(LocalDateTime.of(2017, 4, 15, 16, 55, 0)))
                        .setCompleteDateTime(Timestamp.valueOf(LocalDateTime.of(2017, 4 , 16, 16, 0, 0)))
                        .setPatientId(6)
                        .setPerformerId(55)
                        .build();
        assertEquals(dto, service.get(therapyDTO.getId()).get());

        therapyDTO.setTitle(oldTitle);
        therapyDTO.setDescription(oldDescription);
        service.update(therapyDTO);
    }

    @Test
    public void delete() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService therapyService = new TherapyService(daoManagerMock);
        TherapyDTO dto =
                new TherapyDTO.Builder()
                        .setTitle("title")
                        .setType(TherapyDTO.Type.SURGERY_OPERATION)
                        .setDescription("description")
                        .setAppointmentDateTime(Timestamp.valueOf(LocalDateTime.of(3027, 5, 15, 16, 55, 0)))
                        .setCompleteDateTime(Timestamp.valueOf(LocalDateTime.of(3027, 5, 16, 16, 0, 0)))
                        .setPatientId(6)
                        .setPerformerId(55)
                        .build();

        therapyService.create(dto);
        therapyService.delete(dto.getId());
        assertFalse(therapyService.get(dto.getId()).isPresent());
    }

    @Test
    public void performTherapy() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService therapyService = new TherapyService(daoManagerMock);
        therapyService.performTherapy(14);
        TherapyDTO therapyDTO = therapyService.get(14).get();
        assertTrue(therapyDTO.getCompletionDateTime() != null);
        therapyDTO.setCompletionDateTime(null);
        therapyService.update(therapyDTO);
    }

    @Test
    public void changePerformer() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        TherapyService therapyService = new TherapyService(daoManagerMock);
        therapyService.changePerformer(14, 55);
        TherapyDTO therapyDTO = therapyService.get(14).get();
        assertEquals(55, therapyDTO.getPerformerId());
        therapyDTO.setPerformerId(54);
        therapyService.update(therapyDTO);
    }
}