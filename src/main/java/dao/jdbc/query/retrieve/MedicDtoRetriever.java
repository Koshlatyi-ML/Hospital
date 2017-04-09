package dao.jdbc.query.retrieve;

import domain.dto.MedicDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicDtoRetriever extends AbstractDtoRetriever<MedicDTO> {
    @Override
    protected MedicDTO retrieve(ResultSet resultSet) throws SQLException {
        return new MedicDTO.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .setDepartmentId(resultSet.getLong(4))
                .setCredentialsId(resultSet.getLong(5))
                .build();
    }
}
