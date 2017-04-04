package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.PatientTableInfo;

import java.util.Arrays;
import java.util.List;

public class PatientConstantTableInfo implements PatientTableInfo {

    private static final String TABLE_NAME = "patients";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String DOCTOR_ID_COLUMN = "doctor_id";
    private static final String COMPLAINTS_COLUMN = "complaints";
    private static final String DIAGNOSIS_COLUMN = "diagnosis";
    private static final String STATE_COLUMN = "state";

    PatientConstantTableInfo() {
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? ID_COLUMN
                : TABLE_NAME + "." + ID_COLUMN;
    }

    @Override
    public String getNameColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? NAME_COLUMN
                : TABLE_NAME + "." + NAME_COLUMN;
    }

    @Override
    public String getSurnameColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? SURNAME_COLUMN
                : TABLE_NAME + "." + SURNAME_COLUMN;
    }

    @Override
    public String getDoctorIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? DOCTOR_ID_COLUMN
                : TABLE_NAME + "." + DOCTOR_ID_COLUMN;
    }

    @Override
    public String getDiagnosisColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? DIAGNOSIS_COLUMN
                : TABLE_NAME + "." + DIAGNOSIS_COLUMN;
    }

    @Override
    public String getComplaintsColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? COMPLAINTS_COLUMN
                : TABLE_NAME + "." + COMPLAINTS_COLUMN;
    }

    @Override
    public String getStateColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? STATE_COLUMN
                : TABLE_NAME + "." + STATE_COLUMN;
    }


    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(NAME_COLUMN, SURNAME_COLUMN,
                DOCTOR_ID_COLUMN, COMPLAINTS_COLUMN,
                DIAGNOSIS_COLUMN, STATE_COLUMN);
    }
}
