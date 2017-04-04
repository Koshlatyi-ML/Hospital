package dao.metadata;

public interface PersonTableInfo extends PlainTableInfo {
    String getNameColumn(ColumnNameStyle columnNameStyle);
    String getSurnameColumn(ColumnNameStyle columnNameStyle);
}
