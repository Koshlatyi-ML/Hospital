package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.AbstractDTO;
import domain.dto.DoctorDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DoctorQueryExecutorTest {
    @Spy
    private DoctorQueryExecutor queryExecutor;
    @Mock
    private StuffTableInfo stuffTableInfoMock;
    @Mock
    private PatientTableInfo patientTableInfoMock;
    @Mock
    private DoctorTableInfo doctorTableInfoMock;
    @Mock
    private StuffDtoValueSupplier<DoctorDTO> dtoValueSupplierMock;
    @Mock
    private DtoRetriever<DoctorDTO> dtoRetrieverMock;

    private final String STUFF_TABLE = "stuff_table";
    private final String STUFF_ID = "stuff_table.id";
    private final String STUFF_NAME = "stuff_table.name";
    private final String STUFF_SURNAME = "stuff_table.surname";
    private final String STUFF_DEPARTMENT_ID = "stuff_table.department_id";


    private final String PATIENT_TABLE = "patient_table";
    private final String PATIENT_ID = "patient_table.id";
    private final String PATIENT_DOCTOR_ID = "patient_table.doctor_id";

    private final String DOCTOR_TABLE = "doctor_table";
    private final String DOCTOR_ID = "doctor_table.id";
    private final String DOCTOR_ID_SHORT = "id";
    private final String DOCTOR_A = "doctor_table.a";
    private final String DOCTOR_B = "doctor_table.b";

    private final String selectedFields =
            STUFF_ID + " AS \"" + STUFF_ID + "\"," +
                    STUFF_NAME + " AS \"" + STUFF_NAME + "\"," +
                    STUFF_SURNAME + " AS \"" + STUFF_SURNAME + "\"," +
                    STUFF_DEPARTMENT_ID + " AS \"" + STUFF_DEPARTMENT_ID + "\"";

    private final String findByPatientIdQuery = "SELECT " + selectedFields +
            " FROM " + STUFF_TABLE + " INNER JOIN " + DOCTOR_TABLE +
            " ON " + STUFF_ID + "=" + DOCTOR_ID +
            " INNER JOIN "+ PATIENT_TABLE +
            " ON " + STUFF_ID + "=" + PATIENT_DOCTOR_ID +
            " WHERE " + PATIENT_ID + "=?";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(stuffTableInfoMock.getTableName()).thenReturn(STUFF_TABLE);
        when(stuffTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_ID);
        when(stuffTableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_NAME);
        when(stuffTableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_SURNAME);
        when(stuffTableInfoMock.getDepartmentIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_DEPARTMENT_ID);

        when(patientTableInfoMock.getTableName()).thenReturn(PATIENT_TABLE);
        when(patientTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_ID);
        when(patientTableInfoMock.getDoctorIdColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_DOCTOR_ID);

        when(doctorTableInfoMock.getTableName()).thenReturn(DOCTOR_TABLE);
        when(doctorTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(DOCTOR_ID);
        when(doctorTableInfoMock.getIdColumn(ColumnNameStyle.SHORT)).thenReturn(DOCTOR_ID_SHORT);
        when(doctorTableInfoMock.getNonGeneratingColumns()).thenReturn(Arrays.asList(DOCTOR_A, DOCTOR_B));

        queryExecutor.setStuffTableInfo(stuffTableInfoMock);
        queryExecutor.setPatientTableInfo(patientTableInfoMock);
        queryExecutor.setDoctorTableInfo(doctorTableInfoMock);
        queryExecutor.setValueSupplier(dtoValueSupplierMock);
        queryExecutor.setDtoRetriever(dtoRetrieverMock);
    }

    @Test
    public void getFindAllQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + DOCTOR_TABLE +
                " ON " + STUFF_ID + "=" + DOCTOR_ID + " ", queryExecutor.getFindAllQuery());
    }

    @Test
    public void getFindByIdQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + DOCTOR_TABLE +
                " ON " + STUFF_ID + "=" + DOCTOR_ID +
                " WHERE " + STUFF_ID + "=?", queryExecutor.getFindByIdQuery());
    }

    @Test
    public void getFindByFullNameQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                        " FROM " + STUFF_TABLE + " INNER JOIN " + DOCTOR_TABLE +
                        " ON " + STUFF_ID + "=" + DOCTOR_ID +
                        " WHERE LOWER(" + STUFF_NAME + "||" + STUFF_SURNAME + ") LIKE LOWER(?)",
                queryExecutor.getFindByFullNameQuery());
    }

    @Test
    public void getFindByDepartmentIdQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + DOCTOR_TABLE +
                " ON " + STUFF_ID + "=" + DOCTOR_ID +
                " WHERE " + STUFF_DEPARTMENT_ID + "=?", queryExecutor.getFindByDepartmentIdQuery());
    }

    @Test
    public void getInsertQuery() throws Exception {
        assertEquals("INSERT INTO " + DOCTOR_TABLE +
                " (" + DOCTOR_A +"," + DOCTOR_B + ") VALUES (?,?)",
                queryExecutor.getInsertQuery());
    }

    @Test
    public void getUpdateQuery() throws Exception {
        assertEquals("UPDATE " + DOCTOR_TABLE +
                        " SET (" + DOCTOR_A +"=?," + DOCTOR_B + "=?)" +
                        " WHERE " + DOCTOR_ID_SHORT + "=?",
                queryExecutor.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() throws Exception {
        assertEquals("DELETE FROM " + DOCTOR_TABLE +
                        " WHERE " + DOCTOR_ID_SHORT + "=?",
                queryExecutor.getDeleteQuery());
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertNullConnection() throws Exception {
        queryExecutor.queryInsert(null, mock(DoctorDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertNullDTO() throws Exception {
        queryExecutor.queryInsert(mock(Connection.class), null);
    }

    @Test
    public void queryInsertPreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getInsertQuery(),
                Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        DoctorDTO dtoMock = mock(DoctorDTO.class);

        doNothing().when(queryExecutor).queryInsertStuff(connectionMock, dtoMock) ;

        queryExecutor.queryInsert(connectionMock, dtoMock);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryInsertOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getInsertQuery(),
                Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        DoctorDTO dtoMock = mock(DoctorDTO.class);
        doNothing().when(queryExecutor).queryInsertStuff(connectionMock, dtoMock) ;
        queryExecutor.queryInsert(connectionMock, dtoMock);

        verify(preparedStatementMock, times(1)).execute();
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateNullConnection() throws Exception {
        queryExecutor.queryUpdate(null, mock(DoctorDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateNullDTO() throws Exception {
        queryExecutor.queryUpdate(mock(Connection.class), null);
    }

    @Test
    public void queryUpdatePreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getUpdateQuery()))
                .thenReturn(preparedStatementMock);

        DoctorDTO dtoMock = mock(DoctorDTO.class);
        doNothing().when(queryExecutor).queryUpdateStuff(connectionMock, dtoMock) ;
        queryExecutor.queryUpdate(connectionMock, dtoMock);

        verify(preparedStatementMock).close();
    }

    @Test
    public void queryUpdateOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getUpdateQuery()))
                .thenReturn(preparedStatementMock);

        DoctorDTO dtoMock = mock(DoctorDTO.class);
        doNothing().when(queryExecutor).queryUpdateStuff(connectionMock, dtoMock); ;
        queryExecutor.queryUpdate(connectionMock, dtoMock);

        verify(preparedStatementMock, times(1)).execute();
    }


    @Test(expected = NullPointerException.class)
    public void queryDeleteNullConnection() throws Exception {
        queryExecutor.queryDelete(null, 100L);
    }

    @Test
    public void queryDeletePreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        long id = 100L;
        DoctorDTO dtoMock = mock(DoctorDTO.class);
        when(dtoMock.getId()).thenReturn(id);
        doNothing().when(queryExecutor).queryDeleteStuff(connectionMock, id);
        queryExecutor.queryDelete(connectionMock, dtoMock);

        verify(preparedStatementMock).close();
    }

    @Test
    public void queryDeleteOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutor.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        long id = 100L;
        DoctorDTO dtoMock = mock(DoctorDTO.class);
        when(dtoMock.getId()).thenReturn(id);
        doNothing().when(queryExecutor).queryDeleteStuff(connectionMock, id);
        queryExecutor.queryDelete(connectionMock, dtoMock);

        verify(preparedStatementMock, times(1)).execute();
    }


    @Test
    public void queryDeleteDtoRedirect() throws Exception {
        Connection connectionMock = mock(Connection.class);
        long id = 100L;
        DoctorDTO dtoMock = mock(DoctorDTO.class);
        when(dtoMock.getId()).thenReturn(id);

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        when(connectionMock.prepareStatement(queryExecutor.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        doNothing().when(queryExecutor).queryDeleteStuff(connectionMock, id);

        queryExecutor.queryDelete(connectionMock, dtoMock);

        verify(queryExecutor).queryDelete(connectionMock, dtoMock.getId());
    }

    @Test(expected = NullPointerException.class)
    public void queryDeleteDtoNullDTO() throws Exception {
        queryExecutor.queryDelete(mock(Connection.class), null);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByPatientNullConnection() throws Exception {
        queryExecutor.queryFindByPatientId(null, 100L);
    }

    @Test
    public void queryFindByPatientStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(findByPatientIdQuery))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByPatientId(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByPatientOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(findByPatientIdQuery))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByPatientId(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}