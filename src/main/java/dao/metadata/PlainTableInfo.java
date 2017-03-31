package dao.metadata;

import java.util.List;

public interface PlainTableInfo {
    String getTableName();
    String getIdColumn();
    List<String> getColumns();
}
