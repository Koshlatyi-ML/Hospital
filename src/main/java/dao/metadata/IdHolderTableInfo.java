package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.util.TableInfoUtils;
import domain.IdHolder;

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

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getIdColumnName() {
        return idColumnName;
    }

    protected void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    protected void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    static void fillTableInfo(IdHolderTableInfo tableInfo) {
        Class entityClass = IdHolderTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();
        tableInfo.setIdColumnName(TableInfoUtils.loadIdColumnName(entityClass));
    }
}
