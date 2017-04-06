package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.StuffDtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.PlainTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.AbstractDTO;
import domain.dto.AbstractStuffDTO;
import jdk.nashorn.internal.objects.NativeUint8Array;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StuffQueryExecutorTest {
    @Spy
    private StuffQueryExecutor queryExecutorSpy;
    @Mock
    private StuffTableInfo tableInfoMock;
    @Mock
    private DtoRetriever dtoRetriever;
    @Mock
    private StuffDtoValueSupplier dtoValueSupplier;

    private final String TABLE_NAME = "table";
    private final String ID = "id";
    private final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (b,c) VALUES (?,?)";
    private final String UPDATE_QUERY = "UPDATE " + TABLE_NAME + " SET b=?,c=? WHERE " + ID + "=?";
    private final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=?";

    private final List<String> selectingColumns = Arrays.asList("b", "c");
    private final String ALIASED_SELECTING_COLUMNS = "id AS \"id\",b AS \"b\",c AS \"c\"";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfoMock.getTableName()).thenReturn(TABLE_NAME);
        when(tableInfoMock.getIdColumn(ColumnNameStyle.SHORT)).thenReturn(ID);
        when(tableInfoMock.getNonGeneratingColumns()).thenReturn(selectingColumns);

        when(queryExecutorSpy.getTableInfo()).thenReturn(tableInfoMock);
        when(queryExecutorSpy.getSelectingColumns()).thenReturn(selectingColumns);
        when(queryExecutorSpy.getDtoRetriever()).thenReturn(dtoRetriever);
        when(queryExecutorSpy.getDtoValueSupplier()).thenReturn(dtoValueSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertStuffNullConnection() throws Exception {
        queryExecutorSpy.queryInsertStuff(null, mock(AbstractStuffDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryInsertStuffNullDTO() throws Exception {
        queryExecutorSpy.queryInsertStuff(mock(Connection.class), null);
    }

    @Test
    public void queryInsertSTuffStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(INSERT_QUERY,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        queryExecutorSpy.queryInsertStuff(connectionMock, mock(AbstractStuffDTO.class));
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryInsertStuffOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(INSERT_QUERY,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatementMock);

        ResultSet keysMock = mock(ResultSet.class);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        queryExecutorSpy.queryInsertStuff(connectionMock, mock(AbstractStuffDTO.class));
        verify(preparedStatementMock, times(1)).execute();
    }

    @Test
    public void queryInsertKeysSetted() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(INSERT_QUERY,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatementMock);


        ResultSet keysMock = mock(ResultSet.class);
        when(keysMock.next()).thenReturn(true);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(keysMock);

        AbstractStuffDTO dtoMock = mock(AbstractStuffDTO.class);
        queryExecutorSpy.queryInsertStuff(connectionMock, dtoMock);
        verify(dtoMock).setId(anyLong());
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateStuffNullConnection() throws Exception {
        queryExecutorSpy.queryUpdateStuff(null, mock(AbstractStuffDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void queryUpdateStuffNullDTO() throws Exception {
        queryExecutorSpy.queryUpdateStuff(mock(Connection.class), null);
    }

    @Test
    public void queryUpdateStuffStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatementMock);

        queryExecutorSpy.queryUpdateStuff(connectionMock, mock(AbstractStuffDTO.class));
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryUpdateStuffOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatementMock);
        queryExecutorSpy.queryUpdateStuff(connectionMock, mock(AbstractStuffDTO.class));
        verify(preparedStatementMock, times(1)).execute();
    }

    @Test(expected = NullPointerException.class)
    public void queryDeleteNullConnection() throws Exception {
        queryExecutorSpy.queryDeleteStuff(null, 100L);
    }

    @Test
    public void queryDeletePreparedStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(DELETE_QUERY)).thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDeleteStuff(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryDeleteOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(DELETE_QUERY)).thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDeleteStuff(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).execute();
    }


    @Test
    public void queryDeleteDtoRedirect() throws Exception {
        AbstractStuffDTO dtoMock = mock(AbstractStuffDTO.class);

        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(DELETE_QUERY)).thenReturn(preparedStatementMock);

        queryExecutorSpy.queryDeleteStuff(connectionMock, dtoMock);
        verify(queryExecutorSpy).queryDelete(connectionMock, dtoMock.getId());
    }

    @Test(expected = NullPointerException.class)
    public void queryDeleteDtoNullDTO() throws Exception {
        queryExecutorSpy.queryDeleteStuff(mock(Connection.class), null);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByDepartmentIdNullConnection() throws Exception {
        queryExecutorSpy.queryFindByDepartmentId(null, 100L);
    }

    @Test
    public void queryFindByDepartmentIdStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByDepartmentIdQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindByDepartmentId(connectionMock, 100L);
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByDepartmentIdOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByDepartmentIdQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindByDepartmentId(connectionMock, 100L);
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}