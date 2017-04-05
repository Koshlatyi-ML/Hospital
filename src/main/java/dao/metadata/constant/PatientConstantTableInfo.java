package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.PatientTableInfo;
import util.AbstractBuilder;

import java.util.Arrays;
import java.util.List;

public class PatientConstantTableInfo extends AbstractTableInfo implements PatientTableInfo {
    private String nameColumn;
    private String surnameColumn;
    private String doctorIdColumn;
    private String complaintsColumn;
    private String diagnosisColumn;
    private String stateColumn;

    PatientConstantTableInfo() {}

    @Override
    public String getNameColumn(ColumnNameStyle style) {
        return getColumn(nameColumn, style);
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    @Override
    public String getSurnameColumn(ColumnNameStyle style) {
        return getColumn(surnameColumn, style);
    }

    private void setSurnameColumn(String surnameColumn) {
        this.surnameColumn = surnameColumn;
    }

    @Override
    public String getDoctorIdColumn(ColumnNameStyle style) {
        return getColumn(doctorIdColumn, style);
    }

    private void setDoctorIdColumn(String doctorIdColumn) {
        this.doctorIdColumn = doctorIdColumn;
    }

    @Override
    public String getDiagnosisColumn(ColumnNameStyle style) {
        return getColumn(diagnosisColumn, style);
    }

    private void setDiagnosisColumn(String diagnosisColumn) {
        this.diagnosisColumn = diagnosisColumn;
    }

    @Override
    public String getComplaintsColumn(ColumnNameStyle style) {
        return getColumn(complaintsColumn, style);
    }

    private void setComplaintsColumn(String complaintsColumn) {
        this.complaintsColumn = complaintsColumn;
    }

    @Override
    public String getStateColumn(ColumnNameStyle style) {
        return getColumn(stateColumn, style);
    }

    private void setStateColumn(String stateColumn) {
        this.stateColumn = stateColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(nameColumn, surnameColumn,
                doctorIdColumn, complaintsColumn,
                diagnosisColumn, stateColumn);
    }

    public static class Builder
            extends AbstractTableInfo.Builder<PatientConstantTableInfo, Builder> {

        Builder setNameColumn(String nameColumn) {
            instance.setNameColumn(nameColumn);
            return getSelf();
        }

        Builder setSurnameColumn(String surnameColumn) {
            instance.setSurnameColumn(surnameColumn);
            return getSelf();
        }

        Builder setDoctorIdColumn(String doctorIdColumn) {
            instance.setDoctorIdColumn(doctorIdColumn);
            return getSelf();
        }

        Builder setDiagnosisColumn(String diagnosisColumn) {
            instance.setDiagnosisColumn(diagnosisColumn);
            return getSelf();
        }

        Builder setComplaintsColumn(String complaintsColumn) {
            instance.setComplaintsColumn(complaintsColumn);
            return getSelf();
        }

        Builder setStateColumn(String stateColumn) {
            instance.setStateColumn(stateColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
