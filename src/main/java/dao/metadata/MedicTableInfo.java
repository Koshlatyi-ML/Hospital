package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.Inherit;
import dao.metadata.util.TableInfoUtils;
import domain.Medic;

import java.util.ArrayList;
import java.util.List;

@Entity(Medic.class)
public class MedicTableInfo extends IdHolderTableInfo {
    MedicTableInfo() {}

    public static MedicTableInfo createMedicTableInfo() {
        MedicTableInfo tableInfo = new MedicTableInfo();

        Class<?> entityClass = MedicTableInfo.class
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
