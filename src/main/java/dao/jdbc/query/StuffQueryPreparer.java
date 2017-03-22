package dao.jdbc.query;

import dao.metadata.StuffTableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class StuffQueryPreparer<T extends StuffTableInfo> extends PersonQueryPreparer<T> {
    public PreparedStatement findByDepartmentId(Connection connection)
            throws SQLException {

        return connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = ?;",
                        tableInfo.getTableName(),
                        tableInfo.getDepartmentIdColumn()));
    }
}
