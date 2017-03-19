package dao.metadata;

import dao.metadata.annotation.Entity;
import dao.metadata.util.TableInfoUtils;
import domain.Person;

import java.util.Map;

@Entity(Person.class)
public abstract class PersonTableInfo extends IdHolderTableInfo {
    private String nameColumn;
    private String surnameColumn;

    public String getNameColumn() {
        return nameColumn;
    }

    void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getSurnameColumn() {
        return surnameColumn;
    }

    void setSurnameColumn(String surnameColumn) {
        this.surnameColumn = surnameColumn;
    }

    public static void fillTableInfo(PersonTableInfo tableInfo) {
        IdHolderTableInfo.fillTableInfo(tableInfo);

        Class entityClass = PersonTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("surname");
        tableInfo.setSurnameColumn(column);
        tableInfo.getColumnNames().add(column);
    }
}
