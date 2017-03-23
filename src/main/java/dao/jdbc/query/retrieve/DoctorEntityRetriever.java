package dao.jdbc.query.retrieve;

import domain.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorEntityRetriever extends AbstractEntityRetriever<Doctor> {
    @Override
    protected Doctor retrieve(ResultSet resultSet) throws SQLException {
        return new Doctor.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .build();
    }
}
