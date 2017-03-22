package dao.jdbc.query;

import dao.metadata.PersonTableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonQueryPreparer<T extends PersonTableInfo> extends QueryPreparer<T> {

    public PreparedStatement prepareFindByFullName(Connection connection)
            throws SQLException {

        return connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                        tableInfo.getTableName(),
                        tableInfo.getNameColumn(),
                        tableInfo.getSurnameColumn()));
    }
}
