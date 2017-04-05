package dao.metadata.constant;

import dao.metadata.ColumnNameStyle;
import dao.metadata.PlainTableInfo;
import util.AbstractBuilder;

public abstract class AbstractTableInfo implements PlainTableInfo {
    String tableName;
    String idColumn;

    @Override
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getIdColumn(ColumnNameStyle style) {
        return getColumn(idColumn, style);
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    String getColumn(String column, ColumnNameStyle style) {
        return style == ColumnNameStyle.SHORT
                ? column
                : tableName + "." + column;
    }

    static abstract class Builder<T extends AbstractTableInfo, B extends Builder>
            extends AbstractBuilder<T> {

        public B setTableName(String tableName) {
            instance.setTableName(tableName);
            return getSelf();
        }

        public B setIdColumn(String idColumn) {
            instance.setIdColumn(idColumn);
            return getSelf();
        }

        public abstract B getSelf();
    }
}
