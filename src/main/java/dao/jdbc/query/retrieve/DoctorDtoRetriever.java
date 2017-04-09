package dao.jdbc.query.retrieve;

import domain.dto.DoctorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDtoRetriever extends AbstractDtoRetriever<DoctorDTO> {

    @Override
    protected DoctorDTO retrieve(ResultSet resultSet) throws SQLException {
        return new DoctorDTO.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .setDepartmentId(resultSet.getLong(4))
                .setCredentialsId(resultSet.getLong(5))
                .build();
    }
}
