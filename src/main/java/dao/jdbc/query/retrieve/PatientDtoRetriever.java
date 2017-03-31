package dao.jdbc.query.retrieve;

import dao.metadata.PatientTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Patient;
import domain.dto.PatientDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDtoRetriever extends AbstractDtoRetriever<PatientDTO> {
    private PatientTableInfo tableInfo;

    PatientDtoRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getPatientTableInfo();
    }

    @Override
    protected PatientDTO retrieve(ResultSet resultSet) throws SQLException {
        return new PatientDTO.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .setSurname(resultSet.getString(tableInfo.getSurnameColumn()))
                .setDoctorId(resultSet.getLong(tableInfo.getDoctorIdColumn()))
                .setCompliants(resultSet.getString(tableInfo.getComplaintsColumn()))
                .setDiagnosis(resultSet.getString(tableInfo.getDiagnosisColumn()))
                .setState(PatientDTO.State.valueOf(resultSet.getString(tableInfo.getStateColumn())))
                .build();
    }
}
