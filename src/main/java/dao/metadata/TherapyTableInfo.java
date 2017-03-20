package dao.metadata;

public interface TherapyTableInfo extends IdHolderTableInfo {
    String getNameColumn();
    String getTypeColumn();
    String getDescriptionColumn();
    String getAppointmentDateColumn();
    String getCompleteDateColumn();
    String getPatientIdColumn();
    String getPerformerIdColumn();

}
