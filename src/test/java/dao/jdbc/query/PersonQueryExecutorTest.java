package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.metadata.ColumnNameStyle;
import dao.metadata.PersonTableInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PersonQueryExecutorTest {
    @Spy
    private PersonQueryExecutor queryExecutorSpy;
    @Mock
    private PersonTableInfo tableInfoMock;
    @Mock
    private DtoRetriever dtoRetriever;
    @Mock
    private DtoValueSupplier dtoValueSupplier;

    private final String TABLE_NAME = "table";
    private final String NAME_COLUMN = "name";
    private final String SURNAME_COLUMN = "surname";

    private final List<String> selectingColumns =
            Arrays.asList("id", NAME_COLUMN, SURNAME_COLUMN);
    private final String ALIASED_SELECTING_COLUMNS =
            "id AS \"id\",name AS \"name\",surname AS \"surname\"";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfoMock.getTableName()).thenReturn(TABLE_NAME);
        when(tableInfoMock.getNameColumn(ColumnNameStyle.FULL)).thenReturn(NAME_COLUMN);
        when(tableInfoMock.getSurnameColumn(ColumnNameStyle.FULL)).thenReturn(SURNAME_COLUMN);

        when(queryExecutorSpy.getTableInfo()).thenReturn(tableInfoMock);
        when(queryExecutorSpy.getSelectingColumns()).thenReturn(selectingColumns);
        when(queryExecutorSpy.getDtoRetriever()).thenReturn(dtoRetriever);
        when(queryExecutorSpy.getDtoValueSupplier()).thenReturn(dtoValueSupplier);
    }

    @Test
    public void getFindByFullNameQuery() throws Exception {
        String tested = queryExecutorSpy.getFindByFullNameQuery();
        assertEquals(
                "SELECT " + ALIASED_SELECTING_COLUMNS +
                        " FROM " + TABLE_NAME +
                        " WHERE LOWER(" + NAME_COLUMN + "||" + SURNAME_COLUMN + ")" +
                        " LIKE LOWER(?)",
                tested);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByFullNameNullConnection() throws Exception {
        queryExecutorSpy.queryFindByFullName(null, "Name");
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByFullNameNullFullName() throws Exception {
        queryExecutorSpy.queryFindByFullName(mock(Connection.class), null);
    }

    @Test
    public void queryFindByFullNameStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByFullNameQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindByFullName(connectionMock, "Name");
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindAllOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(queryExecutorSpy.getFindByFullNameQuery()))
                .thenReturn(preparedStatementMock);

        queryExecutorSpy.queryFindByFullName(connectionMock, "Name");
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}