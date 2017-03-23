package dao.jdbc.query.retrieve;

import domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentEntityRetriever extends AbstractEntityRetriever<Department> {
    @Override
    public Department retrieve(ResultSet resultSet) throws SQLException{
        return new Department.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .build();
    }
}
