package dao.metadata;

import java.util.List;

public interface PlainTableInfo {
    String getTableName();
    String getIdColumn(ColumnNameStyle style);
    List<String> getNonGeneratingColumns();
}
