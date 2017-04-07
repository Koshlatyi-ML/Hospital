package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.metadata.*;
import domain.dto.MedicDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MedicQueryExecutorTest {
    @Spy
    private MedicQueryExecutor queryExecutor;
    @Mock
    private StuffTableInfo stuffTableInfoMock;
    @Mock
    private PatientTableInfo patientTableInfoMock;
    @Mock
    private MedicTableInfo medicTableInfo;
    @Mock
    private StuffDtoValueSupplier<MedicDTO> dtoValueSupplierMock;
    @Mock
    private DtoRetriever<MedicDTO> dtoRetrieverMock;

    private final String STUFF_TABLE = "stuff_table";
    private final String STUFF_ID = "stuff_table.id";
    private final String STUFF_NAME = "stuff_table.name";
    private final String STUFF_SURNAME = "stuff_table.surname";
    private final String STUFF_DEPARTMENT_ID = "stuff_table.department_id";

    private final String MEDIC_TABLE = "medic_table";
    private final String MEDIC_ID = "medic_table.id";
    private final String MEDIC_ID_SHORT = "id";
    private final String MEDIC_A = "medic_table.a";
    private final String MEDIC_B = "medic_table.b";

    private final String selectedFields =
            STUFF_ID + " AS \"" + STUFF_ID + "\"," +
                    STUFF_NAME + " AS \"" + STUFF_NAME + "\"," +
                    STUFF_SURNAME + " AS \"" + STUFF_SURNAME + "\"," +
                    STUFF_DEPARTMENT_ID + " AS \"" + STUFF_DEPARTMENT_ID + "\"";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(stuffTableInfoMock.getTableName()).thenReturn(STUFF_TABLE);
        when(stuffTableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_ID);
        when(stuffTableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_NAME);
        when(stuffTableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_SURNAME);
        when(stuffTableInfoMock.getDepartmentIdColumn(ColumnNameStyle.FULL)).thenReturn(STUFF_DEPARTMENT_ID);

        when(medicTableInfo.getTableName()).thenReturn(MEDIC_TABLE);
        when(medicTableInfo.getIdColumn(ColumnNameStyle.FULL)).thenReturn(MEDIC_ID);
        when(medicTableInfo.getIdColumn(ColumnNameStyle.SHORT)).thenReturn(MEDIC_ID_SHORT);
        when(medicTableInfo.getNonGeneratingColumns()).thenReturn(Arrays.asList(MEDIC_A, MEDIC_B));

        queryExecutor.setStuffTableInfo(stuffTableInfoMock);
        queryExecutor.setMedicTableInfo(medicTableInfo);
        queryExecutor.setValueSupplier(dtoValueSupplierMock);
        queryExecutor.setDtoRetriever(dtoRetrieverMock);
    }

    @Test
    public void getFindAllQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + MEDIC_TABLE +
                " ON " + STUFF_ID + "=" + MEDIC_ID + " ", queryExecutor.getFindAllQuery());
    }

    @Test
    public void getFindByIdQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + MEDIC_TABLE +
                " ON " + STUFF_ID + "=" + MEDIC_ID +
                " WHERE " + STUFF_ID + "=?", queryExecutor.getFindByIdQuery());
    }

    @Test
    public void getFindByFullNameQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                        " FROM " + STUFF_TABLE + " INNER JOIN " + MEDIC_TABLE +
                        " ON " + STUFF_ID + "=" + MEDIC_ID +
                        " WHERE LOWER(" + STUFF_NAME + "||" + STUFF_SURNAME + ") LIKE LOWER(?)",
                queryExecutor.getFindByFullNameQuery());
    }

    @Test
    public void getFindByDepartmentIdQuery() throws Exception {
        assertEquals("SELECT " + selectedFields +
                " FROM " + STUFF_TABLE + " INNER JOIN " + MEDIC_TABLE +
                " ON " + STUFF_ID + "=" + MEDIC_ID +
                " WHERE " + STUFF_DEPARTMENT_ID + "=?", queryExecutor.getFindByDepartmentIdQuery());
    }

    @Test
    public void getInsertQuery() throws Exception {
        assertEquals("INSERT INTO " + MEDIC_TABLE +
                        " (" + MEDIC_A +"," + MEDIC_B + ") VALUES (?,?)",
                queryExecutor.getInsertQuery());
    }

    @Test
    public void getUpdateQuery() throws Exception {
        assertEquals("UPDATE " + MEDIC_TABLE +
                        " SET " + MEDIC_A +"=?," + MEDIC_B + "=?" +
                        " WHERE " + MEDIC_ID_SHORT + "=?",
                queryExecutor.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() throws Exception {
        assertEquals("DELETE FROM " + MEDIC_TABLE +
                        " WHERE " + MEDIC_ID_SHORT + "=?",
                queryExecutor.getDeleteQuery());
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertNullConnection() throws Exception {
        queryExecutor.queryInsert(null, mock(MedicDTO.class));
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

        MedicDTO dtoMock = mock(MedicDTO.class);

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

        MedicDTO dtoMock = mock(MedicDTO.class);
        doNothing().when(queryExecutor).queryInsertStuff(connectionMock, dtoMock) ;
        queryExecutor.queryInsert(connectionMock, dtoMock);

        verify(preparedStatementMock, times(1)).execute();
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateNullConnection() throws Exception {
        queryExecutor.queryUpdate(null, mock(MedicDTO.class));
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

        MedicDTO dtoMock = mock(MedicDTO.class);
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

        MedicDTO dtoMock = mock(MedicDTO.class);
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
        MedicDTO dtoMock = mock(MedicDTO.class);
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
        MedicDTO dtoMock = mock(MedicDTO.class);
        when(dtoMock.getId()).thenReturn(id);
        doNothing().when(queryExecutor).queryDeleteStuff(connectionMock, id);
        queryExecutor.queryDelete(connectionMock, dtoMock);

        verify(preparedStatementMock, times(1)).execute();
    }


    @Test
    public void queryDeleteDtoRedirect() throws Exception {
        Connection connectionMock = mock(Connection.class);
        long id = 100L;
        MedicDTO dtoMock = mock(MedicDTO.class);
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

}