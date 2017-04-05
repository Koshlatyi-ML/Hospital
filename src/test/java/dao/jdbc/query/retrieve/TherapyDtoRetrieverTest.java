package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.TherapyTableInfo;
import domain.dto.PatientDTO;
import domain.dto.TherapyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TherapyDtoRetrieverTest {
    private TherapyDtoRetriever dtoRetriever;

    @Mock
    private TherapyTableInfo tableInfoMock;

    private final String COLUMN_1 = "col1";
    private final String COLUMN_2 = "col2";
    private final String COLUMN_3 = "col3";
    private final String COLUMN_4 = "col4";
    private final String COLUMN_5 = "col5";
    private final String COLUMN_6 = "col6";
    private final String COLUMN_7 = "col7";
    private final String COLUMN_8 = "col8";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_1);
        when(tableInfoMock.getTitleColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_2);
        when(tableInfoMock.getTypeColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_3);
        when(tableInfoMock.getDescriptionColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_4);
        when(tableInfoMock.getAppointmentDateColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_5);
        when(tableInfoMock.getCompleteDateColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_6);
        when(tableInfoMock.getPatientIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_7);
        when(tableInfoMock.getPerformerIdColumn(ColumnNameStyle.FULL)).thenReturn(COLUMN_8);

        dtoRetriever = new TherapyDtoRetriever(tableInfoMock);
    }

    @Test(expected = NullPointerException.class)
    public void retrieveNull() throws Exception {
        dtoRetriever.retrieve(null);
    }

    @Test
    public void retrieve() throws Exception {
        final long id = 100L;
        final String title = "test title";
        final String type = TherapyDTO.Type.PHYSIOTHERAPY.toString();
        final String description = "test description";
        final Timestamp appDate = Timestamp.from(Instant.ofEpochMilli(1000));
        final Timestamp complDate = Timestamp.from(Instant.ofEpochMilli(2000));
        final long patientId = 200L;
        final long performerId = 300L;

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(COLUMN_1)).thenReturn(id);
        when(resultSetMock.getString(COLUMN_2)).thenReturn(title);
        when(resultSetMock.getString(COLUMN_3)).thenReturn(type);
        when(resultSetMock.getString(COLUMN_4)).thenReturn(description);
        when(resultSetMock.getTimestamp(COLUMN_5)).thenReturn(appDate);
        when(resultSetMock.getTimestamp(COLUMN_6)).thenReturn(complDate);
        when(resultSetMock.getLong(COLUMN_7)).thenReturn(patientId);
        when(resultSetMock.getLong(COLUMN_8)).thenReturn(performerId);

        TherapyDTO testedDTO = dtoRetriever.retrieve(resultSetMock);
        TherapyDTO desiredDTO =
                new TherapyDTO.Builder()
                        .setId(id)
                        .setTitle(title)
                        .setType(TherapyDTO.Type.valueOf(type))
                        .setDescription(description)
                        .setAppointmentDateTime(appDate)
                        .setCompleteDateTime(complDate)
                        .setPatientId(patientId)
                        .setPerformerId(performerId)
                        .build();

        assertEquals(testedDTO, desiredDTO);
    }
}