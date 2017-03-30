package dao.metadata.constant;

import dao.metadata.TherapyTableInfo;

import java.util.Arrays;
import java.util.List;

public class TherapyConstantTableInfo extends IdHolderConstantTableInfo
        implements TherapyTableInfo {

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
    public String getIdColumn() {
        return ID_COLUMN;
    }

    @Override
    public String getNameColumn() {
        return NAME_COLUMN;
    }

    @Override
    public String getTypeColumn() {
        return TYPE_COLUMN;
    }

    @Override
    public String getDescriptionColumn() {
        return DESCRIPTION_COLUMN;
    }

    @Override
    public String getAppointmentDateColumn() {
        return APPOINTMENT_DATE_COLUMN;
    }

    @Override
    public String getCompleteDateColumn() {
        return COMPLETE_DATE_COLUMN;
    }

    @Override
    public String getPatientIdColumn() {
        return PATIENT_ID_COLUMN;
    }

    @Override
    public String getPerformerIdColumn() {
        return PERFORMER_ID_COLUMN;
    }

    @Override
    public List<String> getColumns() {
        return Arrays.asList(NAME_COLUMN, TYPE_COLUMN,
                DESCRIPTION_COLUMN, APPOINTMENT_DATE_COLUMN,
                COMPLETE_DATE_COLUMN, PATIENT_ID_COLUMN,
                PERFORMER_ID_COLUMN);
    }

    @Override
    public List<String> getEntityfulColumns() {
        return Arrays.asList(NAME_COLUMN, TYPE_COLUMN,
                DESCRIPTION_COLUMN, APPOINTMENT_DATE_COLUMN,
                COMPLETE_DATE_COLUMN, PATIENT_ID_COLUMN);
    }
}
