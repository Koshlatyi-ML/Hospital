package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoctorDTOTest {
    @Test
    public void setIdBuilder() throws Exception {
        long id = 100L;
        DoctorDTO dto = new DoctorDTO.Builder()
                .setId(id)
                .build();

        assertEquals(id, dto.getId());
    }

    @Test
    public void setNameBuilder() throws Exception {
        String name = "test name";
        DoctorDTO dto = new DoctorDTO.Builder()
                .setName(name)
                .build();

        assertEquals(name, dto.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameNullBuilder() throws Exception {
        new DoctorDTO.Builder()
                .setName(null)
                .build();
    }

    @Test
    public void setSurnameBuilder() throws Exception {
        String surname = "test surname";
        DoctorDTO dto = new DoctorDTO.Builder()
                .setSurname(surname)
                .build();

        assertEquals(surname, dto.getSurname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSurnameNullBuilder() throws Exception {
        new DoctorDTO.Builder()
                .setSurname(null)
                .build();
    }

    @Test
    public void setDepartmentIdBuilder() throws Exception {
        long id = 100L;
        DoctorDTO dto = new DoctorDTO.Builder()
                .setDepartmentId(id)
                .build();

        assertEquals(id, dto.getDepartmentId());
    }

    @Test
    public void equalsTrue() throws Exception {
        long id = 100L;
        String name = "test name";
        String surname = "test surname";
        long depId = 200L;

        DoctorDTO dto1 = new DoctorDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(12)
                .build();

        DoctorDTO dto2 = new DoctorDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(12)
                .build();

        assertTrue(dto1.equals(dto2));
    }

    @Test
    public void equalsFalse() throws Exception {
        String name = "test name";
        String surname = "test surname";
        long depId = 200L;

        DoctorDTO dto1 = new DoctorDTO.Builder()
                .setId(101L)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(900)
                .build();

        DoctorDTO dto2 = new DoctorDTO.Builder()
                .setId(101L)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(901)
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
        long depId = 200L;

        DoctorDTO dto1 = new DoctorDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(1)
                .build();

        DoctorDTO dto2 = new DoctorDTO.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setDepartmentId(depId)
                .setCredentialsId(1)
                .build();

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}