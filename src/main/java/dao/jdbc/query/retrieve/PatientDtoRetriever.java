package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
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
                .setId(resultSet.getLong(tableInfo
                        .getIdColumn(ColumnNameStyle.FULL)))
                .setName(resultSet.getString(tableInfo
                        .getNameColumn(ColumnNameStyle.FULL)))
                .setSurname(resultSet.getString(tableInfo
                        .getSurnameColumn(ColumnNameStyle.FULL)))
                .setDoctorId(resultSet.getLong(tableInfo
                        .getDoctorIdColumn(ColumnNameStyle.FULL)))
                .setCompliants(resultSet.getString(tableInfo
                        .getComplaintsColumn(ColumnNameStyle.FULL)))
                .setDiagnosis(resultSet.getString(tableInfo
                        .getDiagnosisColumn(ColumnNameStyle.FULL)))
                .setState(PatientDTO.State.valueOf(
                        resultSet.getString(tableInfo.getStateColumn(ColumnNameStyle.FULL))))
                .build();
    }
}
