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
    private List<String> medicColumns;

    private MedicAnnotTableInfo() {}

    public String getStuffIdColumn() {
        return stuffIdColumn;
    }

    public List<String> getMedicColumns() {
        return medicColumns;
    }

    @Override
    public List<String> getEntityfulColumns() {
        return null;
    }

    void fillTableInfo() {
        super.fillTableInfo();
        Class<?> entityClass = this.getClass()
                .getDeclaredAnnotation(Entity.class).value();

        tableName = AnnotTableInfos.getTableName(entityClass);

        stuffIdColumn = String.format("%s.%s", tableName,
                entityClass.getDeclaredAnnotation(Inherit.class).foreignKey());
        columns.add(stuffIdColumn);

        medicColumns = new ArrayList<>();
        medicColumns.add(stuffIdColumn);
    }

    static MedicAnnotTableInfo createAnnotTableInfo() {
        MedicAnnotTableInfo tableInfo = new MedicAnnotTableInfo();
        tableInfo.fillTableInfo();
        return tableInfo;
    }
}
