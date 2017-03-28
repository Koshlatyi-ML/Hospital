package dao.jdbc.query.retrieve;

import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentEntityRetriever extends AbstractEntityRetriever<Department> {
    private DepartmentTableInfo tableInfo;

    DepartmentEntityRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getDepartmentTableInfo();
    }

    @Override
    public Department retrieve(ResultSet resultSet) throws SQLException{
        return new Department.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .build();
    }
}
