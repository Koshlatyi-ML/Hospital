package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentDTOTest {
    @Test
    public void setIdBuilder() throws Exception {
        long id = 100L;
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setId(id)
                .build();

        assertEquals(id, dto.getId());
    }

    @Test
    public void setNameBuilder() throws Exception {
        String name = "test name";
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setName(name)
                .build();

        assertEquals(name, dto.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameNullBuilder() throws Exception {
        new DepartmentDTO.Builder()
                .setName(null)
                .build();
    }

    @Test
    public void equalsTrueEmptyObject() throws Exception {
        assertTrue(new DepartmentDTO.Builder().build().equals(new DepartmentDTO.Builder().build()));
    }

    @Test
    public void equalsTrue() throws Exception {
        long id = 100L;
        String name = "test name";

        DepartmentDTO dto1 = new DepartmentDTO.Builder()
                .setId(id)
                .setName(name)
                .build();

        DepartmentDTO dto2 = new DepartmentDTO.Builder()
                .setId(id)
                .setName(name)
                .build();

        assertTrue(dto1.equals(dto2));
    }

    @Test
    public void equalsFalse() throws Exception {
        String name = "test name";

        DepartmentDTO dto1 = new DepartmentDTO.Builder()
                .setId(100L)
                .setName(name)
                .build();

        DepartmentDTO dto2 = new DepartmentDTO.Builder()
                .setId(101L)
                .setName(name)
                .build();

        assertFalse(dto1.equals(dto2));
    }

    @Test
    public void hashCodeEmptyObjectsTrue() throws Exception {
        assertEquals(new DepartmentDTO.Builder().build().hashCode(),
                new DepartmentDTO.Builder().build().hashCode());
    }

    @Test
    public void hashCodeTrue() throws Exception {
        long id = 100L;
        String name = "test name";

        DepartmentDTO dto1 = new DepartmentDTO.Builder()
                .setId(id)
                .setName(name)
                .build();

        DepartmentDTO dto2 = new DepartmentDTO.Builder()
                .setId(id)
                .setName(name)
                .build();

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}