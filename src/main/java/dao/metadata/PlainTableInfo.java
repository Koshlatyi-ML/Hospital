package dao.metadata;

import java.util.List;

public interface PlainTableInfo {
    String getTableName();
    String getIdColumnName();
    List<String> getColumnNames();
}
