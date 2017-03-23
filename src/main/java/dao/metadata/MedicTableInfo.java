package dao.metadata;

import java.util.List;

public interface MedicTableInfo extends StuffTableInfo {
    String getStuffIdColumn();
    List<String> getMedicColumns();
}
