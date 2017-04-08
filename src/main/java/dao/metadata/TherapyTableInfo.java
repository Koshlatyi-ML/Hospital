package dao.metadata;

public interface TherapyTableInfo extends PlainTableInfo {
    String getTitleColumn(ColumnNameStyle columnNameStyle);
    String getTypeColumn(ColumnNameStyle columnNameStyle);
    String getDescriptionColumn(ColumnNameStyle columnNameStyle);
    String getAppointmentDateColumn(ColumnNameStyle columnNameStyle);
    String getCompleteDateColumn(ColumnNameStyle columnNameStyle);
    String getPatientIdColumn(ColumnNameStyle columnNameStyle);
    String getPerformerIdColumn(ColumnNameStyle columnNameStyle);

}
