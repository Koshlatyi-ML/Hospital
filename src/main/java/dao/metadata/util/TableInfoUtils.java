package dao.metadata.util;

import dao.metadata.StuffTableInfo;
import dao.metadata.annotation.*;
import dao.metadata.annotation.Column;
import dao.metadata.annotation.Id;
import dao.metadata.annotation.Inherit;
import dao.metadata.annotation.OneToMany;
import dao.metadata.annotation.InheritedBy;
import util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TableInfoUtils {
    public static Map<String, String> loadFieldColumnMap(Class<?> entityClass) {

        Map<String, String> fieldColumnMap  = new HashMap<>();
        Stream.of(entityClass.getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(
                        field, Column.class))
                .forEach(field -> fieldColumnMap.put(field.getName(),
                        field.getAnnotation(Column.class).value())
                );
        return fieldColumnMap;
    }

    public static String getTableName(Class<?> entityClass) {
        return entityClass.getDeclaredAnnotation(Table.class).value();
    }

    public static String getIdColumnName(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(
                        field, Id.class))
                .map(field -> field.getDeclaredAnnotation(Id.class))
                .map(Id::value)
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    public static String getInheritedByTableName(Class<?> entityClass) {
        Class<?> derivedClass = StuffTableInfo.class
                .getDeclaredAnnotation(InheritedBy.class).value();
        return derivedClass.getDeclaredAnnotation(Inherit.class).table();
    }

    public static OneToOne getOneToOneRelation(Class<?> entityClass,
                                               String tableName) {

        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(
                        field, OneToOne.class))
                .map(field -> field.getDeclaredAnnotation(OneToOne.class))
                .filter(annotation -> tableName.equals(annotation.table()))
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    public static OneToMany getOneToManyRelation(Class<?> entityClass,
                                                 String tableName) {

        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(
                        field, OneToMany.class))
                .map(field -> field.getDeclaredAnnotation(OneToMany.class))
                .filter(annotation -> tableName.equals(annotation.table()))
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
