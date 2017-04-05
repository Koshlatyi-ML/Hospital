package domain.dto;

import domain.Therapy;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;

public class TherapyDTOTest {
    @Test
    public void setIdBuilder() throws Exception {
        long id = 100L;
        TherapyDTO dto = new TherapyDTO.Builder()
                .setId(id)
                .build();

        assertEquals(id, dto.getId());
    }

    @Test
    public void setTitleBuilder() throws Exception {
        String title = "test title";
        TherapyDTO dto = new TherapyDTO.Builder()
                .setTitle(title)
                .build();

        assertEquals(title, dto.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTitleNullBuilder() throws Exception {
        new TherapyDTO.Builder()
                .setTitle(null)
                .build();
    }

    @Test
    public void setType() throws Exception {
        TherapyDTO.Type type = TherapyDTO.Type.PHYSIOTHERAPY;
        TherapyDTO dto = new TherapyDTO.Builder()
                .setType(type)
                .build();

        assertEquals(type.toString(), dto.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTypeNullBuilder() throws Exception {
        new TherapyDTO.Builder()
                .setType(null)
                .build();
    }

    @Test
    public void setDescription() throws Exception {
        String description = "test description";
        TherapyDTO dto = new TherapyDTO.Builder()
                .setDescription(description)
                .build();

        assertEquals(description, dto.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDescriptionNullBuilder() throws Exception {
        new TherapyDTO.Builder()
                .setDescription(null)
                .build();
    }

    @Test
    public void setAppointmentDateTimeBuilder() throws Exception {
        Timestamp appTime = Timestamp.from(Instant.ofEpochMilli(1000));
        TherapyDTO dto = new TherapyDTO.Builder()
                .setAppointmentDateTime(appTime)
                .build();

        assertEquals(appTime, dto.getAppointmentDateTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAppointmentDateTimeNullBuilder() throws Exception {
        new TherapyDTO.Builder()
                .setAppointmentDateTime(null)
                .build();
    }

    @Test
    public void setCompleteDateTimeBuilder() throws Exception {
        Timestamp appTime = Timestamp.from(Instant.ofEpochMilli(1000));
        TherapyDTO dto = new TherapyDTO.Builder()
                .setCompleteDateTime(appTime)
                .build();

        assertEquals(appTime, dto.getCompleteDateTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCompleteDateTimeNullBuilder() throws Exception {
        new TherapyDTO.Builder()
                .setCompleteDateTime(null)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void setCompleteDateTimeRepeatedly() throws Exception {
        Timestamp appTime = Timestamp.from(Instant.ofEpochMilli(1000));
        TherapyDTO dto = new TherapyDTO.Builder()
                .setCompleteDateTime(appTime)
                .build();

        dto.setCompleteDateTime(Timestamp.from(Instant.now()));
    }

    @Test
    public void setPatientIdBuilder() throws Exception {
        long patientId = 1000L;
        TherapyDTO dto = new TherapyDTO.Builder()
                .setPatientId(patientId)
                .build();

        assertEquals(patientId, dto.getPatientId());
    }

    @Test
    public void setPerformerIdBuilder() throws Exception {
        long performerId = 1000L;
        TherapyDTO dto = new TherapyDTO.Builder()
                .setPerformerId(performerId)
                .build();

        assertEquals(performerId, dto.getPerformerId());
    }
    @Test(expected = IllegalArgumentException.class)
    public void setStateNullBuilder() throws Exception {
        new PatientDTO.Builder()
                .setState(null)
                .build();
    }

    @Test
    public void equalsTrue() throws Exception {
        long id = 100L;
        String title = "test title";
        TherapyDTO.Type type = TherapyDTO.Type.SURGERY_OPERATION;
        String description = "test description";
        Timestamp appTime = Timestamp.from(Instant.now());
        Timestamp completeTime = Timestamp.from(Instant.now());
        long patientId = 200L;
        long performerId = 300L;

        TherapyDTO dto1 = new TherapyDTO.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        TherapyDTO dto2 = new TherapyDTO.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        assertTrue(dto1.equals(dto2));
    }

    @Test
    public void equalsFalse() throws Exception {
        String title = "test title";
        TherapyDTO.Type type = TherapyDTO.Type.SURGERY_OPERATION;
        String description = "test description";
        Timestamp appTime = Timestamp.from(Instant.now());
        Timestamp completeTime = Timestamp.from(Instant.now());
        long patientId = 200L;
        long performerId = 300L;

        TherapyDTO dto1 = new TherapyDTO.Builder()
                .setId(555L)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        TherapyDTO dto2 = new TherapyDTO.Builder()
                .setId(111L)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        assertFalse(dto1.equals(dto2));
    }

    @Test
    public void equalsTrueEmptyObject() throws Exception {
        assertTrue(new DoctorDTO.Builder().build().equals(new DoctorDTO.Builder().build()));
    }


    @Test
    public void hashCodeEmptyObjectsTrue() throws Exception {
        assertEquals(new DoctorDTO.Builder().build().hashCode(),
                new DoctorDTO.Builder().build().hashCode());
    }

    @Test
    public void hashCodeTrue() throws Exception {
        long id = 100L;
        String title = "test title";
        TherapyDTO.Type type = TherapyDTO.Type.SURGERY_OPERATION;
        String description = "test description";
        Timestamp appTime = Timestamp.from(Instant.now());
        Timestamp completeTime = Timestamp.from(Instant.now());
        long patientId = 200L;
        long performerId = 300L;

        TherapyDTO dto1 = new TherapyDTO.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        TherapyDTO dto2 = new TherapyDTO.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setDescription(description)
                .setAppointmentDateTime(appTime)
                .setCompleteDateTime(completeTime)
                .setPatientId(patientId)
                .setPerformerId(performerId)
                .build();

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}