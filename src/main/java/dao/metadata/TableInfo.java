package dao.metadata;

import java.util.List;

public interface TableInfo {
    String getIdColumnName();
    String getTableName();
    List<String> getColumnNames();
}
