package dao.metadata.annotation;

import dao.metadata.PersonTableInfo;
import dao.metadata.annotation.mapping.Entity;
import dao.metadata.annotation.util.AnnotTableInfos;
import domain.Person;

import java.util.Map;

@Entity(Person.class)
public abstract class PersonAnnotTableInfo extends IdHolderAnnotTableInfo
                                           implements PersonTableInfo {
    private String nameColumn;
    private String surnameColumn;

    public String getNameColumn() {
        return nameColumn;
    }

    private void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getSurnameColumn() {
        return surnameColumn;
    }

    private void setSurnameColumn(String surnameColumn) {
        this.surnameColumn = surnameColumn;
    }

    void fillTableInfo(PersonAnnotTableInfo tableInfo) {
        super.fillTableInfo(tableInfo);

        Class entityClass = PersonAnnotTableInfo.class
                .getDeclaredAnnotation(Entity.class).value();

        Map<String, String> fieldColumnMap
                = AnnotTableInfos.loadFieldColumnMap(entityClass);

        String column = fieldColumnMap.get("name");
        tableInfo.setNameColumn(column);
        tableInfo.getColumnNames().add(column);

        column = fieldColumnMap.get("surname");
        tableInfo.setSurnameColumn(column);
        tableInfo.getColumnNames().add(column);
    }
}
