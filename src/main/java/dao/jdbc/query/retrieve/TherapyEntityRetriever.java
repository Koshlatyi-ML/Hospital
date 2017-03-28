package dao.jdbc.query.retrieve;

import dao.metadata.PatientTableInfo;
import dao.metadata.TableInfoFactory;
import dao.metadata.TherapyTableInfo;
import domain.Patient;
import domain.Therapy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TherapyEntityRetriever extends AbstractEntityRetriever<Therapy> {
    private TherapyTableInfo therapyTableInfo;
    private PatientTableInfo patientTableInfo;

    TherapyEntityRetriever(TableInfoFactory tableInfoFactory) {
        therapyTableInfo = tableInfoFactory.getTherapyTableInfo();
        patientTableInfo = tableInfoFactory.getPatientTableInfo();
    }

    @Override
    protected Therapy retrieve(ResultSet resultSet) throws SQLException {
        return new Therapy.Builder()
                .setId(resultSet
                        .getLong(therapyTableInfo.getIdColumn()))
                .setName(resultSet
                        .getString(therapyTableInfo.getNameColumn()))
                .setDescription(resultSet
                        .getString(therapyTableInfo.getDescriptionColumn()))
                .setType(Therapy.Type.valueOf(resultSet
                        .getString(therapyTableInfo.getTypeColumn())))
                .setAppointmentDateTime(resultSet
                        .getTimestamp(therapyTableInfo.getAppointmentDateColumn())
                        .toInstant())
                .setCompleteDateTime(resultSet
                        .getTimestamp(therapyTableInfo.getCompleteDateColumn())
                        .toInstant())
                .setPatient(new Patient.Builder()
                        .setId(resultSet
                                .getLong(patientTableInfo.getIdColumn()))
                        .setName(resultSet
                                .getString(patientTableInfo.getNameColumn()))
                        .setSurname(resultSet
                                .getString(patientTableInfo.getSurnameColumn()))
                        .setCompliants(resultSet
                                .getString(patientTableInfo.getComplaintsColumn()))
                        .setDiagnosis(resultSet
                                .getString(patientTableInfo.getDiagnosisColumn()))
                        .setState(Patient.State.valueOf(resultSet
                                .getString(patientTableInfo.getStateColumn())))
                        .build())
                .build();
    }
}
