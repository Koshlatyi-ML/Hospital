package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatientDTOTest {
    @Test
    public void setIdBuilder() throws Exception {
        long id = 100L;
        PatientDTO dto = new PatientDTO.Builder()
                .setId(id)
                .build();

        assertEquals(id, dto.getId());
    }

    @Test
    public void setNameBuilder() throws Exception {
        String name = "test name";
        PatientDTO dto = new PatientDTO.Builder()
                .setName(name)
                .build();

        assertEquals(name, dto.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameNullBuilder() throws Exception {
        new PatientDTO.Builder()
                .setName(null)
                .build();
    }

    @Test
    public void setSurnameBuilder() throws Exception {
        String surname = "test surname";
        PatientDTO dto = new PatientDTO.Builder()
                .setSurname(surname)
                .build();

        assertEquals(surname, dto.getSurname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSurnameNullBuilder() throws Exception {
        new PatientDTO.Builder()
                .setSurname(null)
                .build();
    }

    @Test
    public void setDoctorIdBuilder() throws Exception {
        long id = 100L;
        PatientDTO dto = new PatientDTO.Builder()
                .setDoctorId(id)
                .build();

        assertEquals(id, dto.getDoctorId());
    }

    @Test
    public void setComplaintsBuilder() throws Exception {
        String complaints = "test complaints";
        PatientDTO dto = new PatientDTO.Builder()
                .setCompliants(complaints)
                .build();

        assertEquals(complaints, dto.getComplaints());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setComplaintsNullBuilder() throws Exception {
        new PatientDTO.Builder()
                .setCompliants(null)
                .build();
    }

    @Test
    public void setDiagnosisBuilder() throws Exception {
        String diagnosis = "test diagnosis";
        PatientDTO dto = new PatientDTO.Builder()
                .setDiagnosis(diagnosis)
                .build();

        assertEquals(diagnosis, dto.getDiagnosis());
    }

    @Test
    public void setStateBuilder() throws Exception {
        PatientDTO.State state  = PatientDTO.State.TREATED;
        PatientDTO dto = new PatientDTO.Builder()
                .setState(state)
                .build();

        assertEquals(state.toString(), dto.getState());
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
        String name = "test name";
        String surname = "test surname";
        long docId = 200L;
        String complaints = "test compl";
        String diagnosis = "волчанка";
        PatientDTO.State state = PatientDTO.State.APPLIED;

        PatientDTO dto1 = new PatientDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .setCredentialsId(1)
                .build();

        PatientDTO dto2 = new PatientDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .setCredentialsId(1)
                .build();

        assertTrue(dto1.equals(dto2));
    }

    @Test
    public void equalsFalse() throws Exception {
        String name = "test name";
        String surname = "test surname";
        long docId = 200L;
        String complaints = "test compl";
        String diagnosis = "волчанка";
        PatientDTO.State state = PatientDTO.State.APPLIED;

        PatientDTO dto1 = new PatientDTO.Builder()
                .setId(200L)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .setCredentialsId(1)
                .build();

        PatientDTO dto2 = new PatientDTO.Builder()
                .setId(200L)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .setCredentialsId(2)
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
        String name = "test name";
        String surname = "test surname";
        long docId = 200L;
        String complaints = "test compl";
        String diagnosis = "волчанка";
        PatientDTO.State state = PatientDTO.State.APPLIED;

        PatientDTO dto1 = new PatientDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .build();

        PatientDTO dto2 = new PatientDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDoctorId(docId)
                .setCompliants(complaints)
                .setDiagnosis(diagnosis)
                .setState(state)
                .build();

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}