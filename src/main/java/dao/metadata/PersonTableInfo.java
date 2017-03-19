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

    protected void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getSurnameColumn() {
        return surnameColumn;
    }

    protected void setSurnameColumn(String surnameColumn) {
        this.surnameColumn = surnameColumn;
    }

    public static void fillTableInfo(PersonTableInfo tableInfo) {
        Class entityClass = PersonTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        Map<String, String> fieldColumnMap
                = TableInfoUtils.loadFieldColumnMap(entityClass);

        IdHolderTableInfo.fillTableInfo(tableInfo);
        tableInfo.setNameColumn(fieldColumnMap.get("name"));
        tableInfo.setSurnameColumn(fieldColumnMap.get("surname"));
    }
}
