package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DoctorConstantTableInfo extends AbstractTableInfo implements DoctorTableInfo {

    private String credentialsIdColumn;

    static class ColumnNames {
        final static String TABLE_NAME = "doctors";
        final static String STUFF_ID_COLUMN = "stuff_id";
        final static String CREDENTIALS_ID_COLUMN = "credentials_id";
    }

    DoctorConstantTableInfo() {}

    @Override
    public String getCredentialsIdColumn(ColumnNameStyle columnNameStyle) {
        return getColumn(credentialsIdColumn, columnNameStyle);
    }

    private void setCredentialsIdColumn(String credentialsIdColumn) {
        this.credentialsIdColumn = credentialsIdColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(idColumn, credentialsIdColumn);
    }

    public static class Builder extends AbstractTableInfo.Builder<DoctorConstantTableInfo, Builder> {

        public Builder() {
            instance = new DoctorConstantTableInfo();
        }

        Builder setCredentialsIdColumn(String credentialsIdColumn) {
            instance.setCredentialsIdColumn(credentialsIdColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
