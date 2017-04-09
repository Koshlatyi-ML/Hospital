package dao.jdbc.query.retrieve;

import domain.dto.PatientDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDtoRetriever extends AbstractDtoRetriever<PatientDTO> {
    @Override
    protected PatientDTO retrieve(ResultSet resultSet) throws SQLException {
        return new PatientDTO.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .setDoctorId(resultSet.getLong(4))
                .setCompliants(resultSet.getString(5))
                .setDiagnosis(resultSet.getString(6))
                .setState(PatientDTO.State.valueOf(resultSet.getString(7)))
                .setCredentialsId(resultSet.getLong(8))
                .build();
    }
}
