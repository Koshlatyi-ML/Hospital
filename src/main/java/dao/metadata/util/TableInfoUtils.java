package dao.metadata.util;

import dao.metadata.annotation.Column;
import dao.metadata.annotation.Id;
import dao.metadata.annotation.Table;
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

    public static String loadTableName(Class<?> entityClass) {
        return entityClass.getDeclaredAnnotation(Table.class).value();
    }

    public static String loadIdColumnName(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> ReflectionUtils.hasDeclaredAnnotation(
                        field, Id.class))
                .map(field -> field.getDeclaredAnnotation(Id.class))
                .map(Id::value)
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
