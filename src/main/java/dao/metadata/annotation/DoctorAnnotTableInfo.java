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

    private DoctorAnnotTableInfo() { }

    @Override
    public String getStuffIdColumn() {
        return stuffIdColumn;
    }

    public void setStuffIdColumn(String stuffIdColumn) {
        this.stuffIdColumn = stuffIdColumn;
    }

    void fillTableInfo(DoctorAnnotTableInfo tableInfo) {
        Class<?> entityClass = DoctorAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(AnnotTableInfos.getTableName(entityClass));

        String stuffId = entityClass.getDeclaredAnnotation(Inherit.class)
                .foreignKey();
        tableInfo.setStuffIdColumn(stuffId);

        List<String> columnNames = new ArrayList<>();
        columnNames.add(stuffId);
        tableInfo.setColumnNames(columnNames);
    }

    static DoctorAnnotTableInfo createAnnotTableInfo() {
        DoctorAnnotTableInfo tableInfo = new DoctorAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
