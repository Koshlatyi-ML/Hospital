package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.*;
import domain.dto.TherapyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

public class TherapyQueryExecutorTest {

    private TherapyQueryExecutor queryExecutor;
    @Mock
    private TherapyTableInfo tableInfoMock;
    @Mock
    private DoctorTableInfo doctorTableInfoMock;
    @Mock
    private MedicTableInfo medicTableInfoMock;
    @Mock
    private PatientTableInfo patientTableInfoMock;
    @Mock
    private DtoValueSupplier<TherapyDTO> dtoSupplierMock;
    @Mock
    private DtoRetriever<TherapyDTO> dtoRetrieverMock;

    private final String THERAPY_TABLE = "therapy_table";
    private final String THERAPY_ID = "therapy_table.id";
    private final String THERAPY_TITLE = "therapy_table.title";
    private final String THERAPY_TYPE = "therapy_table.type";
    private final String THERAPY_DESCRIPTION = "therapy_table.description";
    private final String THERAPY_APP_DATE = "therapy_table.app_date";
    private final String THERAPY_COMPL_DATE = "therapy_table.compl_date";
    private final String THERAPY_PATIENT_ID = "therapy_table.patient_id";
    private final String THERAPY_PERFORMER_ID = "therapy_table.performer_id";

    private final String DOCTOR_TABLE = "doctor_table";
    private final String DOCTOR_ID = "doctor_table.id";

    private final String MEDIC_TABLE = "medic_table";
    private final String MEDIC_ID = "medic_table.id";

    private final String PATIENT_TABLE = "patient_table";
    private final String PATIENT_ID = "patient_table.id";

    private final String selectedFields =
            THERAPY_ID + " AS \"" + THERAPY_ID + "\"," +
                    THERAPY_TITLE + " AS \"" + THERAPY_TITLE + "\"," +
                    THERAPY_TYPE + " AS \"" + THERAPY_TYPE + "\"," +
                    THERAPY_DESCRIPTION + " AS \"" + THERAPY_DESCRIPTION + "\"," +
                    THERAPY_APP_DATE + " AS \"" + THERAPY_APP_DATE + "\"," +
                    THERAPY_COMPL_DATE + " AS \"" + THERAPY_COMPL_DATE + "\"," +
                    THERAPY_PATIENT_ID + " AS \"" + THERAPY_PATIENT_ID + "\"," +
                    THERAPY_PERFORMER_ID + " AS \"" + THERAPY_PERFORMER_ID + "\"";

    private final String FIND_BY_PATIENT_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + PATIENT_TABLE +
                    " ON " + THERAPY_PATIENT_ID + "=" + PATIENT_ID +
                    " WHERE " + PATIENT_ID + "=? AND " + THERAPY_TYPE + "=?";

    private final String FIND_CURRENT_BY_PATIENT_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + PATIENT_TABLE +
                    " ON " + THERAPY_PATIENT_ID + "=" + PATIENT_ID +
                    " WHERE " + PATIENT_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NULL" +
                    " AND " + THERAPY_APP_DATE + "<now()";

    private final String FIND_FINISHED_BY_PATIENT_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + PATIENT_TABLE +
                    " ON " + THERAPY_PATIENT_ID + "=" + PATIENT_ID +
                    " WHERE " + PATIENT_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NOT NULL";

    private final String FIND_FUTURE_BY_PATIENT_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + PATIENT_TABLE +
                    " ON " + THERAPY_PATIENT_ID + "=" + PATIENT_ID +
                    " WHERE " + PATIENT_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_APP_DATE + ">now()";

    private final String FIND_BY_MEDIC_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + MEDIC_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + MEDIC_ID +
                    " WHERE " + MEDIC_ID + "=? AND " + THERAPY_TYPE + "=?";

    private final String FIND_CURRENT_BY_MEDIC_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + MEDIC_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + MEDIC_ID +
                    " WHERE " + MEDIC_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NULL" +
                    " AND " + THERAPY_APP_DATE + "<now()";

    private final String FIND_FINISHED_BY_MEDIC_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + MEDIC_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + MEDIC_ID +
                    " WHERE " + MEDIC_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NOT NULL";

    private final String FIND_FUTURE_BY_MEDIC_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + MEDIC_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + MEDIC_ID +
                    " WHERE " + MEDIC_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_APP_DATE + ">now()";

    private final String FIND_BY_DOCTOR_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + DOCTOR_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + DOCTOR_ID +
                    " WHERE " + DOCTOR_ID + "=? AND " + THERAPY_TYPE + "=?";

    private final String FIND_CURRENT_BY_DOCTOR_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + DOCTOR_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + DOCTOR_ID +
                    " WHERE " + DOCTOR_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NULL" +
                    " AND " + THERAPY_APP_DATE + "<now()";

    private final String FIND_FINISHED_BY_DOCTOR_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + DOCTOR_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + DOCTOR_ID +
                    " WHERE " + DOCTOR_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_COMPL_DATE + " IS NOT NULL";

    private final String FIND_FUTURE_BY_DOCTOR_ID_AND_TYPE_QUERY =
            "SELECT " + selectedFields + " FROM " + THERAPY_TABLE +
                    " INNER JOIN " + DOCTOR_TABLE +
                    " ON " + THERAPY_PERFORMER_ID + "=" + DOCTOR_ID +
                    " WHERE " + DOCTOR_ID + "=? AND " + THERAPY_TYPE + "=?" +
                    " AND " + THERAPY_APP_DATE + ">now()";


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfoMock.getTableName()).thenReturn(THERAPY_TABLE);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_ID);
        when(tableInfoMock.getTitleColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_TITLE);
        when(tableInfoMock.getTypeColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_TYPE);
        when(tableInfoMock.getDescriptionColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_DESCRIPTION);
        when(tableInfoMock.getAppointmentDateColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_APP_DATE);
        when(tableInfoMock.getCompleteDateColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_COMPL_DATE);
        when(tableInfoMock.getPatientIdColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_PATIENT_ID);
        when(tableInfoMock.getPerformerIdColumn(ColumnNameStyle.FULL)).thenReturn(THERAPY_PERFORMER_ID);

        when(patientTableInfoMock.getTableName()).thenReturn(PATIENT_TABLE);
        when(patientTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(PATIENT_ID);

        when(doctorTableInfoMock.getTableName()).thenReturn(DOCTOR_TABLE);
        when(doctorTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(DOCTOR_ID);

        when(medicTableInfoMock.getTableName()).thenReturn(MEDIC_TABLE);
        when(medicTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(MEDIC_ID);

        queryExecutor = new TherapyQueryExecutor();
        queryExecutor.setTableInfo(tableInfoMock);
        queryExecutor.setMedicTableInfo(medicTableInfoMock);
        queryExecutor.setPatientTableInfo(patientTableInfoMock);
        queryExecutor.setDoctorTableInfo(doctorTableInfoMock);
        queryExecutor.setDtoValueSupplier(dtoSupplierMock);
        queryExecutor.setDtoRetriever(dtoRetrieverMock);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByPatientIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindByPatientIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByPatientIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindByPatientIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindByPatientIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByPatientIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByNameOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByPatientIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrentByPatientIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindCurrentByPatientIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrentByPatientIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindCurrentByPatientIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindCurrentByPatientIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindCurrentByPatientIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindCurrentByPatientIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindCurrentByPatientIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByPatientIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFinishedByPatientIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByPatientIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFinishedByPatientIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFinishedByPatientIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFinishedByPatientIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFinishedByPatientIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFinishedByPatientIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByPatientIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFutureByPatientIdAndType(null, 100L,
                TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByPatientIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFutureByPatientIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFutureByPatientIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFutureByPatientIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFutureByPatientIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_PATIENT_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFutureByPatientIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }


    @Test(expected = NullPointerException.class)
    public void queryFindByMedicIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindByMedicIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByMedicIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindByMedicIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindByMedicIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByMedicIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByMedicIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByMedicIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrentByMedicIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindCurrentByMedicIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrentByMedicIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindCurrentByMedicIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindCurrentByMedicIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindCurrentByMedicIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindCurrentByMedicIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindCurrentByMedicIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByMedicIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFinishedByMedicIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByMedicIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFinishedByMedicIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFinishedByMedicIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFinishedByMedicIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFinishedByMedicIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFinishedByMedicIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByMedicIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFutureByMedicIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByMedicIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFutureByMedicIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFutureByMedicIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFutureByMedicIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFutureByMedicIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_MEDIC_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFutureByMedicIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByDoctorIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindByDoctorIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByDoctorIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindByDoctorIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindByDoctorIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByDoctorIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByDoctorIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByDoctorIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrenrByDoctorIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindCurrentByDoctorIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindCurrentByDoctorIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindCurrentByDoctorIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindCurrentByDoctorIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindCurrentByDoctorIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindCurrentByDoctorIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_CURRENT_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindCurrentByDoctorIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByDoctorIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFinishedByDoctorIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFinishedByDoctorIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFinishedByDoctorIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFinishedByDoctorIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFinishedByDoctorIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFinishedByDoctorIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FINISHED_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFinishedByDoctorIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByDoctorIdAndTypeNullConnection() throws Exception {
        queryExecutor.queryFindFutureByDoctorIdAndType(null, 100L, TherapyDTO.Type.PHARMACOTHERAPY);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindFutureByDoctorIdAndTypeNullType() throws Exception {
        queryExecutor.queryFindFutureByDoctorIdAndType(mock(Connection.class), 100L, null);
    }

    @Test
    public void queryFindFutureByDoctorIdAndTypeStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindFutureByDoctorIdAndType(connectionMock,
                100L, TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindFutureByDoctorIdAndTypeOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_FUTURE_BY_DOCTOR_ID_AND_TYPE_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindFutureByDoctorIdAndType(connectionMock, 100L,
                TherapyDTO.Type.SURGERY_OPERATION);
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}
