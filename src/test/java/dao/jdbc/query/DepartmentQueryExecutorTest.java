package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.DtoValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.dto.AbstractStuffDTO;
import domain.dto.DepartmentDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DepartmentQueryExecutorTest {
    private DepartmentQueryExecutor queryExecutor;
    @Mock
    private DepartmentTableInfo tableInfo;
    @Mock
    private DtoValueSupplier<DepartmentDTO> dtoSupplier;
    @Mock
    private DtoRetriever<DepartmentDTO> dtoRetriever;

    private final String TABLE_NAME = "table";
    private final String ID_COLUMN = "id";
    private final String NAME_COLUMN = "name";
    private final String FIND_BY_NAME_QUERY =
            "SELECT id AS \"id\",name AS \"name\" FROM table WHERE name=?";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tableInfo.getTableName()).thenReturn(TABLE_NAME);
        when(tableInfo.getNameColumn(ColumnNameStyle.FULL)).thenReturn(NAME_COLUMN);
        when(tableInfo.getIdColumn(ColumnNameStyle.FULL)).thenReturn(ID_COLUMN);

        queryExecutor = new DepartmentQueryExecutor();
        queryExecutor.setTableInfo(tableInfo);
        queryExecutor.setDtoValueSupplier(dtoSupplier);
        queryExecutor.setDtoRetriever(dtoRetriever);
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByNameNullConnection() throws Exception {
        queryExecutor.queryFindByName(null, "name");
    }

    @Test(expected = NullPointerException.class)
    public void queryFindByNameNullName() throws Exception {
        queryExecutor.queryFindByName(mock(Connection.class), null);
    }

    @Test
    public void queryFindByNameStatementClosed() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_NAME_QUERY))
                .thenReturn(preparedStatementMock);

        queryExecutor.queryFindByName(connectionMock, "rand");
        verify(preparedStatementMock).close();
    }

    @Test
    public void queryFindByNameOneQuery() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(FIND_BY_NAME_QUERY))
                .thenReturn(preparedStatementMock);


        queryExecutor.queryFindByName(connectionMock, "rand");
        verify(preparedStatementMock, times(1)).executeQuery();
    }
}