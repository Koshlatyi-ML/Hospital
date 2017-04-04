package dao.jdbc.query;

import dao.metadata.ColumnNameStyle;
import dao.metadata.PersonTableInfo;
import domain.dto.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonQueryExecutor<T extends AbstractPersonDTO> extends QueryExecutor<T> {

    String getFindByFullNameQuery() {
        return String.format("SELECT %s FROM %s WHERE LOWER(%s || %s) LIKE LOWER(?);",
                Queries.formatAliasedColumns(getSelectingColumns()),
                getTableInfo().getTableName(),
                getTableInfo().getNameColumn(ColumnNameStyle.FULL),
                getTableInfo().getSurnameColumn(ColumnNameStyle.FULL));
    }

    public List<T> queryFindByFullName(Connection connection, String fullName)
            throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByFullNameQuery())) {
            statement.setString(1, "%" + fullName + "%");
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDTOList(resultSet);
        }
    }

    @Override
    protected abstract PersonTableInfo getTableInfo();
}
