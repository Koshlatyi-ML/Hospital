package dao.metadata.annotation;

import dao.metadata.DoctorTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.mapping.Inherit;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Doctor;

import java.util.ArrayList;
import java.util.List;

@Entity(Doctor.class)
public class DoctorAnnotTableInfo extends StuffAnnotTableInfo
                                  implements DoctorTableInfo {
    private String stuffIdColumn;
    private List<String> doctorColumns;

    private DoctorAnnotTableInfo() { }

    public List<String> getDoctorColumns() {
        return doctorColumns;
    }

    public String getStuffIdColumn() {
        return stuffIdColumn;
    }

    @Override
    public List<String> getEntityfulColumns() {
        return null;
    }

    void fillTableInfo() {
        super.fillTableInfo();
        Class<?> entityClass = DoctorAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        tableName = AnnotTableInfos.getTableName(entityClass);

        stuffIdColumn = String.format("%s.%s", tableName,
                entityClass.getDeclaredAnnotation(Inherit.class).foreignKey());
        columns.add(stuffIdColumn);

        doctorColumns = new ArrayList<>();
        doctorColumns.add(stuffIdColumn);
    }

    static DoctorAnnotTableInfo createAnnotTableInfo() {
        DoctorAnnotTableInfo tableInfo = new DoctorAnnotTableInfo();
        tableInfo.fillTableInfo();
        return tableInfo;
    }
}
