package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.PatientTableInfo;
import domain.dto.PatientDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientDtoRetrieverTest {
    private PatientDtoRetriever dtoRetriever;

    @Mock
    private PatientTableInfo tableInfoMock;

    private final String COLUMN_ONE = "col1";
    private final String COLUMN_TWO = "col2";
    private final String COLUMN_THREE = "col3";
    private final String COLUMN_FOUR = "col4";
    private final String COLUMN_FIVE = "col5";
    private final String COLUMN_SIX = "col6";
    private final String COLUMN_SEVEN = "col7";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_ONE);
        when(tableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_TWO);
        when(tableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_THREE);
        when(tableInfoMock.getDoctorIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_FOUR);
        when(tableInfoMock.getComplaintsColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_FIVE);
        when(tableInfoMock.getDiagnosisColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_SIX);
        when(tableInfoMock.getStateColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_SEVEN);

        dtoRetriever = new PatientDtoRetriever(tableInfoMock);
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
        final long doctor_id = 101L;
        final String complaints = "test complaints";
        final String diagnosis = "test diagnosis";
        final String state = PatientDTO.State.DISCHARGED.toString();

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(COLUMN_ONE)).thenReturn(id);
        when(resultSetMock.getString(COLUMN_TWO)).thenReturn(name);
        when(resultSetMock.getString(COLUMN_THREE)).thenReturn(surname);
        when(resultSetMock.getLong(COLUMN_FOUR)).thenReturn(doctor_id);
        when(resultSetMock.getString(COLUMN_FIVE)).thenReturn(complaints);
        when(resultSetMock.getString(COLUMN_SIX)).thenReturn(diagnosis);
        when(resultSetMock.getString(COLUMN_SEVEN)).thenReturn(state);

        PatientDTO testedDTO = dtoRetriever.retrieve(resultSetMock);
        PatientDTO desiredDTO =
                new PatientDTO.Builder()
                        .setId(id)
                        .setName(name)
                        .setSurname(surname)
                        .setDoctorId(doctor_id)
                        .setCompliants(complaints)
                        .setDiagnosis(diagnosis)
                        .setState(PatientDTO.State.valueOf(state))
                        .build();

        assertEquals(testedDTO, desiredDTO);
    }
}