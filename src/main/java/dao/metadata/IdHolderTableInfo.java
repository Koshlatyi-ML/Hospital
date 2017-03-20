package dao.metadata;

import java.util.List;

public interface IdHolderTableInfo {
    String getTableName();
    String getIdColumnName();
    List<String> getColumnNames();
}
