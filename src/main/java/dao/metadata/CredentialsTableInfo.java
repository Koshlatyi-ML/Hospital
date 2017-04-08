package dao.metadata;

public interface CredentialsTableInfo extends PlainTableInfo {
    String getLoginColumn(ColumnNameStyle columnNameStyle);
    String getPasswordColumn(ColumnNameStyle columnNameStyle);
    String getRoleColumn(ColumnNameStyle columnNameStyle);
}
