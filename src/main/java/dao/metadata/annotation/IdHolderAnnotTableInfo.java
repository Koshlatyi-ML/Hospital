package dao.metadata.annotation;

import dao.connection.ConnectionFactory;
import dao.metadata.PlainTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.IdHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(IdHolder.class)
public abstract class IdHolderAnnotTableInfo implements PlainTableInfo {
    String tableName;
    private String idColumn;
    List<String> columns;

    public String getTableName() {
        return tableName;
    }

    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    void fillTableInfo() {
        Class entityClass = IdHolderAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        idColumn = AnnotTableInfos.getIdColumnName(entityClass);
        columns = new ArrayList<>();
    }
}
