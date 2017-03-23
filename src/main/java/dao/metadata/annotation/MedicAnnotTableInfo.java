package dao.metadata.annotation;

import dao.metadata.MedicTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.mapping.Inherit;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Medic;

import java.util.ArrayList;
import java.util.List;

@Entity(Medic.class)
public class MedicAnnotTableInfo extends StuffAnnotTableInfo
                                 implements MedicTableInfo {
    private String stuffIdColumn;

    private MedicAnnotTableInfo() {}

    @Override
    public String getStuffIdColumn() {
        return null;
    }

    public void setStuffIdColumn(String stuffIdColumn) {
        this.stuffIdColumn = stuffIdColumn;
    }

    void fillTableInfo(MedicAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        Class<?> entityClass = this.getClass()
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setTableName(AnnotTableInfos.getTableName(entityClass));

        String stuffId = entityClass.getDeclaredAnnotation(Inherit.class)
                .foreignKey();
        tableInfo.setStuffIdColumn(stuffId);

        List<String> columnNames = new ArrayList<>();
        columnNames.add(stuffId);
        tableInfo.setColumnNames(columnNames);
    }

    static MedicAnnotTableInfo createAnnotTableInfo() {
        MedicAnnotTableInfo tableInfo = new MedicAnnotTableInfo();
        tableInfo.fillTableInfo(tableInfo);
        return tableInfo;
    }
}
