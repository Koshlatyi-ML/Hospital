package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.PlainTableInfo;
import domain.dto.AbstractDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueryExecutorTest {
    @Spy
    private QueryExecutor queryExecutorSpy;
    @Mock
    private PlainTableInfo tableInfoMock;
    @Mock
    private DtoRetriever dtoRetriever;
    @Mock
    private DtoValueSupplier dtoValueSupplier;

    private final String TABLE_NAME = "table";
    private final String ID = "id";

    private final List<String> selectingColumns = Arrays.asList(ID, "b", "c");
    private final String ALIASED_SELECTING_COLUMNS = "id AS \"id\",b AS \"b\",c AS \"c\"";

    private final List<String> nonGeneratingColumns = Arrays.asList("b", "c");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfoMock.getTableName()).thenReturn(TABLE_NAME);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.FULL)).thenReturn(ID);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.SHORT)).thenReturn(ID);
        when(tableInfoMock.getNonGeneratingColumns()).thenReturn(nonGeneratingColumns);

        when(queryExecutorSpy.getTableInfo()).thenReturn(tableInfoMock);
        when(queryExecutorSpy.getSelectingColumns()).thenReturn(selectingColumns);
        when(queryExecutorSpy.getDtoRetriever()).thenReturn(dtoRetriever);
        when(queryExecutorSpy.getDtoValueSupplier()).thenReturn(dtoValueSupplier);
    }

    @Test
    public void getFindAllQuery() throws Exception {
        String tested = queryExecutorSpy.getFindAllQuery();
        assertEquals(
                "SELECT " + ALIASED_SELECTING_COLUMNS +
                        " FROM " + TABLE_NAME,
                tested);
    }

    @Test
    public void getFindByIdQuery() throws Exception {
        String tested = queryExecutorSpy.getFindByIdQuery();
        assertEquals(
                "SELECT " + ALIASED_SELECTING_COLUMNS +
                        " FROM " + TABLE_NAME +
                        " WHERE " + ID + "=?",
                tested);
    }

    @Test
    public void getInsertQuery() throws Exception {
        String tested = queryExecutorSpy.getInsertQuery();
        assertEquals(
                "INSERT INTO " + TABLE_NAME + " (b,c)" +
                        " VALUES (?,?)",
                tested);
    }

    @Test
    public void getUpdateQuery() throws Exception {
        String tested = queryExecutorSpy.getUpdateQuery();
        assertEquals(
                "UPDATE " + TABLE_NAME +
                        " SET b=?,c=?" +
                        " WHERE " + ID + "=?",
                tested);
    }

    @Test
    public void getDeleteQuery() throws Exception {
        String tested = queryExecutorSpy.getDeleteQuery();
        assertEquals(
                "DELETE FROM " + TABLE_NAME +
                        " WHERE " + ID + "=?",
                tested);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindAllNull() throws Exception {
        queryExecutorSpy.queryFindAll(null);
    }

    @Test
    public void queryFindAllStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindAllQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindAll(connectionMock);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindAllOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindAllQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindAll(connectionMock);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByIdNullConnection() throws Exception {
        queryExecutorSpy.queryFindById(null, 100);
    }

    @Test
    public void queryFindByIdStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByIdQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindById(connectionMock, 100);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByIdOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByIdQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindById(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).executeQuery();
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertNullConnection() throws Exception {
        queryExecutorSpy.queryInsert(null, mock(AbstractDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertNullDTO() throws Exception {
        queryExecutorSpy.queryInsert(mock(Connection.class), null);
    }

    @Test
    public void queryInsertPreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getInsertQuery(),
                Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        queryExecutorSpy.queryInsert(connectionMock, mock(AbstractDTO.class));
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryInsertOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getInsertQuery(),
                Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        queryExecutorSpy.queryInsert(connectionMock, mock(AbstractDTO.class));
        verify(preparedStatementMock, times(1)).execute();
    }

    @Test
    public void queryInsertKeysSetted() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getInsertQuery(),
                Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(keysMock.next()).thenReturn(true);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        AbstractDTO dtoMock = mock(AbstractDTO.class);
        queryExecutorSpy.queryInsert(connectionMock, dtoMock);
        verify(dtoMock).setId(anyLong());
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateNullConnection() throws Exception {
        queryExecutorSpy.queryUpdate(null, mock(AbstractDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateNullDTO() throws Exception {
        queryExecutorSpy.queryUpdate(mock(Connection.class), null);
    }

    @Test
    public void queryUpdatePreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getUpdateQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryUpdate(connectionMock, mock(AbstractDTO.class));
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryUpdateOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getUpdateQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryUpdate(connectionMock, mock(AbstractDTO.class));
        verify(preparedStatementMock, times(1)).execute();
    }

    @Test(expected = NullPointerException.class)
    public void queryDeleteNullConnection() throws Exception {
        queryExecutorSpy.queryDelete(null, 100L);
    }

    @Test
    public void queryDeletePreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDelete(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryDeleteOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDelete(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).execute();
    }


    @Test
    public void queryDeleteDtoRedirect() throws Exception {
        Connection connectionMock = mock(Connection.class);
        AbstractDTO dtoMock = mock(AbstractDTO.class);

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        when(connectionMock.prepareStatement(queryExecutorSpy.getDeleteQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDelete(connectionMock, dtoMock);
        verify(queryExecutorSpy).queryDelete(connectionMock, dtoMock.getId());
    }

    @Test(expected = NullPointerException.class)
    public void queryDeleteDtoNullDTO() throws Exception {
        queryExecutorSpy.queryDelete(mock(Connection.class), null);
    }
}