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

    @Override
    public List<String> getDoctorColumns() {
        return doctorColumns;
    }

    public void setDoctorColumns(List<String> doctorColumns) {
        this.doctorColumns = doctorColumns;
    }

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

        doctorColumns = new ArrayList<>();
        doctorColumns.add(stuffId);
    }

    static DoctorAnnotTableInfo createAnnotTableInfo() {
        DoctorAnnotTableInfo tableInfo = new DoctorAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
