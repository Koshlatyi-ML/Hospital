package dao.jdbc.query.retrieve;

import domain.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientEntityRetriever extends AbstractEntityRetriever<Patient> {
    @Override
    protected Patient retrieve(ResultSet resultSet) throws SQLException {
        return new Patient.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .setCompliants(resultSet.getString(4))
                .setDiagnosis(resultSet.getString(5))
                .setS
    }
}
