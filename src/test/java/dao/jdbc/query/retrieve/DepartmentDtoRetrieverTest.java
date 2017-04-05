package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.dto.DepartmentDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentDtoRetrieverTest {
    private DepartmentDtoRetriever dtoRetriever;

    @Mock
    private DepartmentTableInfo tableInfoMock;

    private final String COLUMN_ONE = "col1";
    private final String COLUMN_TWO = "col2";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_ONE);
        when(tableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_TWO);

        dtoRetriever = new DepartmentDtoRetriever(tableInfoMock);
    }

    @Test(expected = NullPointerException.class)
    public void retrieveNull() throws Exception {
        dtoRetriever.retrieve(null);
    }

    @Test
    public void retrieve() throws Exception {
        final long id = 100L;
        final String name = "test name";

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(COLUMN_ONE)).thenReturn(id);
        when(resultSetMock.getString(COLUMN_TWO)).thenReturn(name);

        DepartmentDTO testedDTO = dtoRetriever.retrieve(resultSetMock);
        DepartmentDTO desiredDTO =
                new DepartmentDTO.Builder()
                        .setId(id)
                        .setName(name)
                        .build();

        assertEquals(desiredDTO, testedDTO);
    }
}