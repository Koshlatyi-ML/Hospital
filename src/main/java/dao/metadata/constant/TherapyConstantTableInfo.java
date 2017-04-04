package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.TherapyTableInfo;

import java.util.Arrays;
import java.util.List;

public class TherapyConstantTableInfo implements TherapyTableInfo {

    private static final String TABLE_NAME = "therapies";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String TYPE_COLUMN = "type";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String APPOINTMENT_DATE_COLUMN = "appointment_date";
    private static final String COMPLETE_DATE_COLUMN = "complete_date";
    private static final String PATIENT_ID_COLUMN = "patient_id";
    private static final String PERFORMER_ID_COLUMN = "performer_id";

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
    public String getTypeColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? TYPE_COLUMN
                : TABLE_NAME + "." + TYPE_COLUMN;
    }

    @Override
    public String getDescriptionColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? DESCRIPTION_COLUMN
                : TABLE_NAME + "." + DESCRIPTION_COLUMN;
    }

    @Override
    public String getAppointmentDateColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? APPOINTMENT_DATE_COLUMN
                : TABLE_NAME + "." + APPOINTMENT_DATE_COLUMN;
    }

    @Override
    public String getCompleteDateColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? COMPLETE_DATE_COLUMN
                : TABLE_NAME + "." + COMPLETE_DATE_COLUMN;
    }

    @Override
    public String getPatientIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? PATIENT_ID_COLUMN
                : TABLE_NAME + "." + PATIENT_ID_COLUMN;
    }

    @Override
    public String getPerformerIdColumn(ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? PERFORMER_ID_COLUMN
                : TABLE_NAME + "." + PERFORMER_ID_COLUMN;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(NAME_COLUMN, TYPE_COLUMN,
                DESCRIPTION_COLUMN, APPOINTMENT_DATE_COLUMN,
                COMPLETE_DATE_COLUMN, PATIENT_ID_COLUMN,
                PERFORMER_ID_COLUMN);
    }
}
