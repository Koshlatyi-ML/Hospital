package dao.jdbc.query.supply;

import domain.PatientDTO;

import java.sql.PreparedStatement;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Types;

public class PatientDtoValueSupplier implements DtoValueSupplier<PatientDTO> {
    PatientDtoValueSupplier() {
    }

    @Override
    public int supplyValues(PreparedStatement statement, PatientDTO dto) throws SQLException {
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getSurname());
        statement.setString(4, dto.getComplaints());
        statement.setString(5, dto.getDiagnosis());
        statement.setString(6, dto.getState());
        statement.setLong(7, dto.getCredentialsId());
        if (dto.getDoctorId() == 0) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setLong(3, dto.getDoctorId());
        }
        return 7;
    }
}
