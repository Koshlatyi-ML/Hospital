package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.annotation.Inherit;
import dao.metadata.util.TableInfoUtils;
import domain.Medic;

@Entity(Medic.class)
public class MedicTableInfo extends StuffTableInfo {
    private String stuffIdColumn;

    private MedicTableInfo() {}

    public String getStuffIdColumn() {
        return stuffIdColumn;
    }

    protected void setStuffIdColumn(String stuffIdColumn) {
        this.stuffIdColumn = stuffIdColumn;
    }

    public static MedicTableInfo createMedicTableInfo() {
        MedicTableInfo tableInfo = new MedicTableInfo();
        fillTableInfo(tableInfo);

        Class<?> entityClass = MedicTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(TableInfoUtils.getTableName(entityClass));

        String stuffId = entityClass.getDeclaredAnnotation(Inherit.class)
                .foreignKey();
        tableInfo.setStuffIdColumn(stuffId);

        // arranging get all cols logic

        return tableInfo;
    }
}
