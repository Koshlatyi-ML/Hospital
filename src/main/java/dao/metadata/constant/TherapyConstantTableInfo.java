package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.TherapyTableInfo;

import java.util.Arrays;
import java.util.List;

public class TherapyConstantTableInfo extends AbstractTableInfo implements TherapyTableInfo {

    private String titleColumn;
    private String typeColumn;
    private String descriptionColumn;
    private String appointmentDateColumn;
    private String completeDateColumn;
    private String patientIdColumn;
    private String performerIdColumn;

    TherapyConstantTableInfo() {}

    @Override
    public String getTitleColumn(ColumnNameStyle style) {
        return getColumn(titleColumn, style);
    }

    private void setTitleColumn(String titleColumn) {
        this.titleColumn = titleColumn;
    }

    @Override
    public String getTypeColumn(ColumnNameStyle style) {
        return getColumn(typeColumn, style);
    }

    private void setTypeColumn(String typeColumn) {
        this.typeColumn = typeColumn;
    }

    @Override
    public String getDescriptionColumn(ColumnNameStyle style) {
        return getColumn(descriptionColumn, style);
    }

    private void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }

    @Override
    public String getAppointmentDateColumn(ColumnNameStyle style) {
        return getColumn(appointmentDateColumn, style);
    }

    private void setAppointmentDateColumn(String appointmentDateColumn) {
        this.appointmentDateColumn = appointmentDateColumn;
    }

    @Override
    public String getCompleteDateColumn(ColumnNameStyle style) {
        return getColumn(completeDateColumn, style);
    }

    private void setCompleteDateColumn(String completeDateColumn) {
        this.completeDateColumn = completeDateColumn;
    }

    @Override
    public String getPatientIdColumn(ColumnNameStyle style) {
        return getColumn(patientIdColumn, style);
    }

    private void setPatientIdColumn(String patientIdColumn) {
        this.patientIdColumn = patientIdColumn;
    }

    @Override
    public String getPerformerIdColumn(ColumnNameStyle style) {
        return getColumn(performerIdColumn, style);
    }

    private void setPerformerIdColumn(String performerIdColumn) {
        this.performerIdColumn = performerIdColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(titleColumn, typeColumn,
                descriptionColumn, appointmentDateColumn,
                completeDateColumn, patientIdColumn,
                performerIdColumn);
    }

    public static class Builder
            extends AbstractTableInfo.Builder<TherapyConstantTableInfo, Builder> {

        Builder setTitleColumn(String titleColumn) {
            instance.setTitleColumn(titleColumn);
            return getSelf();
        }

        Builder setTypeColumn(String typeColumn) {
            instance.setTypeColumn(typeColumn);
            return getSelf();
        }

        Builder setDescriptionColumn(String descriptionColumn) {
            instance.setDescriptionColumn(descriptionColumn);
            return getSelf();
        }

        Builder setAppointmentDateColumn(String appointmentDateColumn) {
            instance.setAppointmentDateColumn(appointmentDateColumn);
            return getSelf();
        }

        Builder setCompleteDateColumn(String completeDateColumn) {
            instance.setCompleteDateColumn(completeDateColumn);
            return getSelf();
        }

        Builder setPatientIdColumn(String patientIdColumn) {
            instance.setPatientIdColumn(patientIdColumn);
            return getSelf();
        }

        Builder setPerformerIdColumn(String performerIdColumn) {
            instance.setPerformerIdColumn(performerIdColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
