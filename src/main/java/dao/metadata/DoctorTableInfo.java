package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.Inherit;
import dao.metadata.util.TableInfoUtils;
import domain.Doctor;

import java.util.ArrayList;
import java.util.List;

@Entity(Doctor.class)
public class DoctorTableInfo extends IdHolderTableInfo {
    DoctorTableInfo() { }

    public static DoctorTableInfo createDoctorTableInfo() {
        DoctorTableInfo tableInfo = new DoctorTableInfo();

        Class<?> entityClass = DoctorTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        String stuffId = entityClass.getDeclaredAnnotation(Inherit.class)
                .foreignKey();
        tableInfo.setIdColumnName(stuffId);

        List<String> columnNames = new ArrayList<>();
        columnNames.add(stuffId);
        tableInfo.setColumnNames(columnNames);

        return tableInfo;
    }
}
