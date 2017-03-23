package dao.metadata;

import java.util.List;

public interface DoctorTableInfo extends StuffTableInfo {
    String getStuffIdColumn();
    List<String> getDoctorColumns();
}
