package dao.metadata;

import java.util.List;

public interface StuffTableInfo extends PersonTableInfo {
    String getStuffTableName();
    String getDepartmentIdColumn();
    List<String> getStuffColumns();
}
