package dao.metadata.constant;

import dao.metadata.PatientTableInfo;

import java.util.Arrays;
import java.util.List;

public class PatientConstantTableInfo extends PersonConstantTableInfo
        implements PatientTableInfo {

    private static final String TABLE_NAME = "patients";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN= "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String DOCTOR_ID_COLUMN= "doctor_id";
    private static final String COMPLAINTS_COLUMN= "complaints";
    private static final String DIAGNOSIS_COLUMN = "diagnosis";
    private static final String STATE_COLUMN = "state";

    PatientConstantTableInfo() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn() {
        return ID_COLUMN;
    }

    @Override
    public String getNameColumn() {
        return NAME_COLUMN;
    }

    @Override
    public String getSurnameColumn() {
        return SURNAME_COLUMN;
    }

    @Override
    public String getDoctorIdColumn() {
       return DOCTOR_ID_COLUMN;
    }

    @Override
    public String getDiagnosisColumn() {
        return DIAGNOSIS_COLUMN;
    }

    @Override
    public String getComplaintsColumn() {
        return COMPLAINTS_COLUMN;
    }

    @Override
    public String getStateColumn() {
        return STATE_COLUMN;
    }


    @Override
    public List<String> getColumns() {
        return Arrays.asList(NAME_COLUMN, SURNAME_COLUMN,
                DOCTOR_ID_COLUMN, COMPLAINTS_COLUMN, DIAGNOSIS_COLUMN,
                STATE_COLUMN);
    }
}
