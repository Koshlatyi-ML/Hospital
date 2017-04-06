package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.*;
import domain.Patient;
import domain.dto.PatientDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class PatientQueryExecutorTest {

    private PatientQueryExecutor queryExecutor;
    @Mock
    private PatientTableInfo tableInfoMock;
    @Mock
    private StuffTableInfo stuffTableInfoMock;
    @Mock
    private DoctorTableInfo doctorTableInfoMock;
    @Mock
    private DtoValueSupplier<PatientDTO> dtoSupplierMock;
    @Mock
    private DtoRetriever<PatientDTO> dtoRetrieverMock;

    private final String PATIENT_TABLE = "patient_table";
    private final String PATIENT_ID = "patient_table.id";
    private final String PATIENT_NAME = "patient_table.name";
    private final String PATIENT_SURNAME = "patient_table.surname";
    private final String PATIENT_DOCTOR_ID = "patient_table.doctor_id";
    private final String PATIENT_COMPLAINTS = "patient_table.complaints";
    private final String PATIENT_DIAGNOSIS = "patient_table.diagnosis";
    private final String PATIENT_STATE = "patient_table.state";

    private final String DOCTOR_TABLE = "doctor_table";
    private final String DOCTOR_ID = "doctor_table.id";

    private final String STUFF_TABLE = "stuff_table";
    private final String STUFF_ID = "stuff_table.id";
    private final String STUFF_DEPARTMENT_ID = "stuff_table.department_id";

    private final String selectedFields =
            PATIENT_ID + " AS \"" + PATIENT_ID + "\"," +
                    PATIENT_NAME + " AS \"" + PATIENT_NAME + "\"," +
                    PATIENT_SURNAME + " AS \"" + PATIENT_SURNAME + "\"," +
                    PATIENT_DOCTOR_ID + " AS \"" + PATIENT_DOCTOR_ID + "\"," +
                    PATIENT_COMPLAINTS + " AS \"" + PATIENT_COMPLAINTS + "\"," +
                    PATIENT_DIAGNOSIS + " AS \"" + PATIENT_DIAGNOSIS + "\"," +
                    PATIENT_STATE + " AS \"" + PATIENT_STATE + "\"";

    private final String FIND_BY_DEPARTMENT_ID =
            "SELECT " + selectedFields +
                    " FROM " + PATIENT_TABLE + " INNER JOIN " + DOCTOR_TABLE +
                    " ON " + PATIENT_DOCTOR_ID + "=" + DOCTOR_ID +
                    " INNER JOIN " + STUFF_TABLE +
                    " ON " + DOCTOR_ID + "=" + STUFF_ID +
                    " WHERE " + STUFF_DEPARTMENT_ID + "=?";

    private final String FIND_BY_DOCTOR_ID =
            "SELECT " + selectedFields +
                    " FROM " + PATIENT_TABLE +
                    " WHERE " + PATIENT_DOCTOR_ID + "=?";

    private final String FIND_BY_STATE =
            "SELECT " + selectedFields +
                    " FROM " + PATIENT_TABLE +
                    " WHERE " + PATIENT_STATE + "=?";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfoMock.getTableName()).thenReturn(PATIENT_TABLE);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_ID);
        when(tableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_NAME);
        when(tableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_SURNAME);
        when(tableInfoMock.getDoctorIdColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_DOCTOR_ID);
        when(tableInfoMock.getComplaintsColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_COMPLAINTS);
        when(tableInfoMock.getDiagnosisColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_DIAGNOSIS);
        when(tableInfoMock.getStateColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_STATE);

        when(stuffTableInfoMock.getTableName()).thenReturn(STUFF_TABLE);
        when(stuffTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_ID);
        when(stuffTableInfoMock.getDepartmentIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_DEPARTMENT_ID);


        when(doctorTableInfoMock.getTableName()).thenReturn(DOCTOR_TABLE);
        when(doctorTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(DOCTOR_ID);

        queryExecutor = new PatientQueryExecutor();
        queryExecutor.setTableInfo(tableInfoMock);
        queryExecutor.setStuffTableInfo(stuffTableInfoMock);
        queryExecutor.setDoctorTableInfo(doctorTableInfoMock);
        queryExecutor.setDtoValueSupplier(dtoSupplierMock);
        queryExecutor.setDtoRetriever(dtoRetrieverMock);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByDepartmentIdNullConnection() throws Exception {
        queryExecutor.queryFindByDepartmentId(null, 100L);
    }

    @Test
    public void queryFindByDepartmentIdStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DEPARTMENT_ID))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByDepartmentId(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByDepartmentIdOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DEPARTMENT_ID))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByDepartmentId(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByDoctorIdNullConnection() throws Exception {
        queryExecutor.queryFindByDoctorId(null, 100L);
    }

    @Test
    public void queryFindByDoctorIdStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DOCTOR_ID))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByDoctorId(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByDoctorIdOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DOCTOR_ID))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByDoctorId(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByStateNullConnection() throws Exception {
        queryExecutor.queryFindByState(null, Patient.State.DISCHARGED);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByStateNullState() throws Exception {
        queryExecutor.queryFindByState(mock(Connection.class), null);
    }

    @Test
    public void queryFindByStateStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_STATE))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByState(connectionMock, Patient.State.ADDMITTED);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByStateOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_STATE))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByState(connectionMock, Patient.State.APPLYIED);
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}