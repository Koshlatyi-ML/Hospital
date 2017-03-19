package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.Inherit;
import dao.metadata.util.TableInfoUtils;
import domain.Doctor;

@Entity(Doctor.class)
public class DoctorTableInfo extends StuffTableInfo {
    private String stuffIdColumn;

    DoctorTableInfo() { }

    public String getStuffIdColumn() {
        return stuffIdColumn;
    }

    public void setStuffIdColumn(String stuffIdColumn) {
        this.stuffIdColumn = stuffIdColumn;
    }

    public static DoctorTableInfo createDoctorTableInfo() {
        DoctorTableInfo tableInfo = new DoctorTableInfo();
        fillTableInfo(tableInfo);

        Class<?> entityClass = DoctorTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        String stuffId = entityClass.getDeclaredAnnotation(Inherit.class)
                .foreignKey();
        tableInfo.setStuffIdColumn(stuffId);

        // arranging get all cols logic

        return tableInfo;
    }
}
