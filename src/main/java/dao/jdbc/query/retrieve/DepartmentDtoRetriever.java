package dao.jdbc.query.retrieve;

import domain.DepartmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDtoRetriever extends AbstractDtoRetriever<DepartmentDTO> {

    @Override
    public DepartmentDTO retrieve(ResultSet resultSet) throws SQLException{
        return new DepartmentDTO.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .build();
    }
}
