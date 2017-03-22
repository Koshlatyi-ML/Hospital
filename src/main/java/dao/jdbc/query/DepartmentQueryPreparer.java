package dao.jdbc.query;

import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentQueryPreparer extends QueryPreparer<DepartmentTableInfo> {
    public DepartmentQueryPreparer() {
        tableInfo = TableInfoFactory.getInstance().getDepartmentTableInfo();
    }

    public PreparedStatement prepareFindByName(Connection connection)
            throws SQLException {

        return connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = ?;",
                        tableInfo.getTableName(),
                        tableInfo.getNameColumn()));
    }

}
