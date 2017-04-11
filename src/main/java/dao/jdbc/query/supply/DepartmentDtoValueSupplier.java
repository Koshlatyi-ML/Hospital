package dao.jdbc.query.supply;

import domain.DepartmentDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentDtoValueSupplier implements DtoValueSupplier<DepartmentDTO> {
    DepartmentDtoValueSupplier() {}

    @Override
    public int supplyValues(PreparedStatement statement, DepartmentDTO dto)
            throws SQLException {

        statement.setString(1, dto.getName());
        return 1;
    }
}
