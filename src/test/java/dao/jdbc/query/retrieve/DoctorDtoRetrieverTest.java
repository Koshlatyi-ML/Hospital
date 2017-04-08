package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.DoctorDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoctorDtoRetrieverTest {
    private DoctorDtoRetriever dtoRetriever;

    @Mock
    private StuffTableInfo stuffTableInfoMock;
    @Mock
    private DoctorTableInfo doctorTableInfoMock;

    private final String COLUMN_ONE = "col1";
    private final String COLUMN_TWO = "col2";
    private final String COLUMN_THREE = "col3";
    private final String COLUMN_FOUR = "col4";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(stuffTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_ONE);
        when(stuffTableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_TWO);
        when(stuffTableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_THREE);
        when(stuffTableInfoMock.getDepartmentIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_FOUR);

        dtoRetriever = new DoctorDtoRetriever(stuffTableInfoMock, doctorTableInfoMock);
    }

    @Test(expected = NullPointerException.class)
    public void retrieveNull() throws Exception {
        dtoRetriever.retrieve(null);
    }

    @Test
    public void retrieve() throws Exception {
        final long id = 100L;
        final String name = "test name";
        final String surname = "test surname";
        final long department_id = 101L;

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(COLUMN_ONE)).thenReturn(id);
        when(resultSetMock.getString(COLUMN_TWO)).thenReturn(name);
        when(resultSetMock.getString(COLUMN_THREE)).thenReturn(surname);
        when(resultSetMock.getLong(COLUMN_FOUR)).thenReturn(department_id);

        DoctorDTO testedDTO = dtoRetriever.retrieve(resultSetMock);
        DoctorDTO desiredDTO =
                new DoctorDTO.Builder()
                        .setId(id)
                        .setName(name)
                        .setSurname(surname)
                        .setDepartmentId(department_id)
                        .build();

        assertEquals(testedDTO, desiredDTO);
    }
}