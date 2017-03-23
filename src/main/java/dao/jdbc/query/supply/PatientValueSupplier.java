package dao.jdbc.query.supply;

import domain.Patient;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientValueSupplier implements ValueSupplier<Patient> {
    @Override
    public void supplyValues(PreparedStatement statement, Patient entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getComplaints());
        statement.setString(4, entity.getDiagnosis());
        statement.setString(5, entity.getState().toString());
    }
}
