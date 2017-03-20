package dao.metadata.annotation;

import dao.metadata.PlainTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.IdHolder;

import java.util.ArrayList;
import java.util.List;

@Entity(IdHolder.class)
public abstract class IdHolderAnnotTableInfo implements PlainTableInfo {
    private String tableName;
    private String idColumnName;
    private List<String> columnNames;

    public String getTableName() {
        return tableName;
    }

    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    void fillTableInfo(IdHolderAnnotTableInfo tableInfo) {
        Class entityClass = IdHolderAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        String idColumn = AnnotTableInfos.getIdColumnName(entityClass);
        tableInfo.setIdColumnName(idColumn);
        tableInfo.setColumnNames(new ArrayList<>());
    }
}
