package dao.jdbc.query;

import dao.jdbc.supply.ValueSupplier;
import domain.IdHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {
    private QueryExecutor(){}

    public static ResultSet executeWithoutArgs(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeIdBased(PreparedStatement preparedStatement,
                                           long id) {
        try {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends IdHolder> ResultSet executeAllColumnsBased(
            PreparedStatement preparedStatement,
            ValueSupplier<T> valueSupplier,
            T entity) {

        try {
            valueSupplier.supplyValues(preparedStatement, entity);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
