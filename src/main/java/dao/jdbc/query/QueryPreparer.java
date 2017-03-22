package dao.jdbc.query;

import dao.metadata.PlainTableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;

public class QueryPreparer<T extends PlainTableInfo> {
    protected T tableInfo;

    public PreparedStatement prepareFindAll(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("SELECT * FROM %s;",
                        tableInfo.getTableName()));
    }

    public PreparedStatement prepareFindById(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s=?;",
                        tableInfo.getTableName(),
                        tableInfo.getIdColumnName()));
    }

    public PreparedStatement prepareInsert(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("INSERT INTO %s %s VALUES %s;",
                        tableInfo.getTableName(),
                        Queries.formatColumnNames(tableInfo),
                        Queries.formatPlaceholders(tableInfo)));
    }

    public PreparedStatement prepareUpdate(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("UPDATE %s SET %s WHERE %s=?;",
                        Queries.formatColumnNames(tableInfo),
                        Queries.formatColumnPlaceholders(tableInfo),
                        tableInfo.getIdColumnName()));
    }

    public PreparedStatement prepareDelete(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("DELETE FROM %s WHERE %s=?;",
                        Queries.formatColumnNames(tableInfo),
                        tableInfo.getIdColumnName()));
    }
}
