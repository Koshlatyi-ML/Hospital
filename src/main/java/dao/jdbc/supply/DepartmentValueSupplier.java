package dao.jdbc.supply;

import domain.Department;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentValueSupplier implements ValueSupplier<Department> {
    @Override
    public void supplyValues(PreparedStatement statement,
                             Department entity) throws SQLException {
        statement.setString(1, entity.getName());
    }
}
