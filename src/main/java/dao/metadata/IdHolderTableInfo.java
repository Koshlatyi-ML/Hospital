package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.util.TableInfoUtils;
import domain.IdHolder;

import java.util.ArrayList;
import java.util.List;

@Entity(IdHolder.class)
public abstract class IdHolderTableInfo implements TableInfo {
    private String tableName;
    private String idColumnName;
    private List<String> columnNames;

    @Override
    public String getTableName() {
        return tableName;
    }

    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getIdColumnName() {
        return idColumnName;
    }

    void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    static void fillTableInfo(IdHolderTableInfo tableInfo) {
        Class entityClass = IdHolderTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        String idColumn = TableInfoUtils.getIdColumnName(entityClass);
        tableInfo.setIdColumnName(idColumn);

        List<String> columnNames = new ArrayList<>();
        columnNames.add(idColumn);
        tableInfo.setColumnNames(columnNames);
    }
}
