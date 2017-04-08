package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.CredentialsTableInfo;

import java.util.Arrays;
import java.util.List;

public class CredentialsConstantTableInfo extends AbstractTableInfo
        implements CredentialsTableInfo {
    private String loginColumn;
    private String passwordColumn;
    private String roleColumn;

    static class ColumnNames {
        final static String TABLE_NAME = "credentials";
        final static String ID = "id";
        final static String LOGIN = "login";
        final static String PASSWORD = "password";
        final static String ROLE = "role";
    }

    CredentialsConstantTableInfo() {}

    @Override
    public String getLoginColumn(ColumnNameStyle columnNameStyle) {
        return getColumn(loginColumn, columnNameStyle);
    }

    private void setLoginColumn(String loginColumn) {
        this.loginColumn = loginColumn;
    }

    @Override
    public String getPasswordColumn(ColumnNameStyle columnNameStyle) {
        return getColumn(passwordColumn, columnNameStyle);
    }

    private void setPasswordColumn(String passwordColumn) {
        this.passwordColumn = passwordColumn;
    }

    @Override
    public String getRoleColumn(ColumnNameStyle columnNameStyle) {
        return getColumn(roleColumn, columnNameStyle);
    }

    private void setRoleColumn(String roleColumn) {
        this.roleColumn = roleColumn;
    }

    @Override
    public List<String> getNonGeneratingColumns() {
        return Arrays.asList(loginColumn, passwordColumn, roleColumn);
    }

    public static class Builder
            extends AbstractTableInfo.Builder<CredentialsConstantTableInfo, Builder> {

        public Builder() {
            instance = new CredentialsConstantTableInfo();
        }

        Builder setLoginColumn(String loginColumn) {
            instance.setLoginColumn(loginColumn);
            return getSelf();
        }

        Builder setPasswordColumn(String passwordColumn) {
            instance.setPasswordColumn(passwordColumn);
            return getSelf();
        }

        Builder setRoleColumn(String roleColumn) {
            instance.setRoleColumn(roleColumn);
            return getSelf();
        }

        @Override
        public Builder getSelf() {
            return this;
        }
    }
}
