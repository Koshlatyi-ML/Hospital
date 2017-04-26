package service;

import dao.DaoManager;
import dao.exception.DaoException;
import dao.jdbc.TestJdbcDaoManager;
import domain.DepartmentDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentServiceTest {

        @Test
        public void getSize() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            assertEquals(4, service.getSize());
        }

        @Test
        public void getAll() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            assertEquals(4, service.getAll(0, 5).size());
        }

        @Test
        public void get() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            DepartmentDTO dto =
                    new DepartmentDTO.Builder()
                            .setId(95)
                            .setName("department1")
                            .build();

            assertEquals(dto, service.get(95).get());
        }

        @Test
        public void getEmpty() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            assertFalse(service.get(-1).isPresent());
        }

        @Test
        public void create() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            DepartmentDTO dto =
                    new DepartmentDTO.Builder()
                            .setName("test-name")
                            .build();
            service.create(dto);
            assertEquals(dto, service.get(dto.getId()).get());
            service.delete(dto.getId());
        }

        @Test
        public void update() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);
            String name = "testedName";

            DepartmentDTO departmentDTO = service.get(95).get();
            String oldName = departmentDTO.getName();

            departmentDTO.setName(name);
            service.update(departmentDTO);

            DepartmentDTO dto =
                    new DepartmentDTO.Builder()
                            .setId(departmentDTO.getId())
                            .setName(name)
                            .build();
            assertEquals(dto, service.get(departmentDTO.getId()).get());

            departmentDTO.setName(oldName);
            service.update(departmentDTO);
        }

        @Test(expected = DaoException.class)
        public void deleteRestrict() throws Exception {
            DaoManager daoManagerMock = new TestJdbcDaoManager();
            DepartmentService service = new DepartmentService(daoManagerMock);

            DepartmentDTO dto = service.get(95).get();
            service.delete(dto.getId());
        }

    @Test
    public void delete() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DepartmentService service = new DepartmentService(daoManagerMock);
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setName("name")
                .build();

        service.create(dto);
        service.delete(dto.getId());
        assertFalse(service.get(dto.getId()).isPresent());
    }

    @Test
    public void getByName() throws Exception {
        DaoManager daoManagerMock = new TestJdbcDaoManager();
        DepartmentService service = new DepartmentService(daoManagerMock);
        DepartmentDTO dto = new DepartmentDTO.Builder()
                .setId(95)
                .setName("department1")
                .build();

        assertEquals(dto, service.getByName("department1").get());
    }
}