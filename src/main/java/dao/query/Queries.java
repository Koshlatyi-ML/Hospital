package dao.query;

import dao.metadata.PlainTableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;

public class Queries {
    private Queries(){}

    private static String formatColumnNames(PlainTableInfo tableInfo) {
        return "(" + String.join(",", tableInfo.getColumnNames()) + ")";
    }

    private static String formatPlaceholders(PlainTableInfo tableInfo) {
        int count = tableInfo.getColumnNames().size();
        return "(" + String.join(",", Collections.nCopies(count, "?")) + ")";
    }

    private static String formatColumnPlaceholders(PlainTableInfo tableInfo) {
        return String.join("=?,", tableInfo.getColumnNames());
    }

    public static PreparedStatement createFindAllQuery(Connection connection,
                                                       PlainTableInfo tableInfo) {
        try {
            return connection.prepareStatement(
                    String.format("SELECT * FROM %s;",
                            tableInfo.getTableName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createFindByIdQuery(Connection connection,
                                                        PlainTableInfo tableInfo) {
        try {
            return connection.prepareStatement(
                    String.format("SELECT * FROM %s WHERE %s=?;",
                            tableInfo.getTableName(),
                            tableInfo.getIdColumnName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createInsertQuery(Connection connection,
                                                      PlainTableInfo tableInfo) {
        try {
            return connection.prepareStatement(
                    String.format("INSERT INTO %s %s VALUES %s;",
                            tableInfo.getTableName(),
                            formatColumnNames(tableInfo),
                            formatPlaceholders(tableInfo)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createUpdateQuery(Connection connection,
                                                      PlainTableInfo tableInfo) {
        try {
            return connection.prepareStatement(
                    String.format("UPDATE %s SET %s WHERE %s=?;",
                            formatColumnNames(tableInfo),
                            formatColumnPlaceholders(tableInfo),
                            tableInfo.getIdColumnName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement createDeleteQuery(Connection connection,
                                                      PlainTableInfo tableInfo) {
        try {
            return connection.prepareStatement(
                    String.format("DELETE FROM %s WHERE %s=?;",
                            formatColumnNames(tableInfo),
                            tableInfo.getIdColumnName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
