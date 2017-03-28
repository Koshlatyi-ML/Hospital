package dao.jdbc.query.retrieve;

import dao.metadata.PatientTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientEntityRetriever extends AbstractEntityRetriever<Patient> {
    private PatientTableInfo tableInfo;

    PatientEntityRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getPatientTableInfo();
    }

    @Override
    protected Patient retrieve(ResultSet resultSet) throws SQLException {
        return new Patient.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .setSurname(resultSet.getString(tableInfo.getSurnameColumn()))
                .setCompliants(resultSet.getString(tableInfo.getComplaintsColumn()))
                .setDiagnosis(resultSet.getString(tableInfo.getDiagnosisColumn()))
                .setState(Patient.State.valueOf(resultSet.getString(tableInfo.getStateColumn())))
                .build();
    }
}
