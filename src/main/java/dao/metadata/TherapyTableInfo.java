package dao.metadata;

public interface TherapyTableInfo extends PlainTableInfo {
    String TABLE_NAME = "therapies";
    String ID_COLUMN = "id";
    String TITLE_COLUMN = "title";
    String TYPE_COLUMN = "type";
    String DESCRIPTION_COLUMN = "description";
    String APPOINTMENT_DATE_COLUMN = "appointment_date";
    String COMPLETE_DATE_COLUMN = "complete_date";
    String PATIENT_ID_COLUMN = "patient_id";
    String PERFORMER_ID_COLUMN = "performet_id";

    String getTitleColumn(ColumnNameStyle columnNameStyle);
    String getTypeColumn(ColumnNameStyle columnNameStyle);
    String getDescriptionColumn(ColumnNameStyle columnNameStyle);
    String getAppointmentDateColumn(ColumnNameStyle columnNameStyle);
    String getCompleteDateColumn(ColumnNameStyle columnNameStyle);
    String getPatientIdColumn(ColumnNameStyle columnNameStyle);
    String getPerformerIdColumn(ColumnNameStyle columnNameStyle);

}
