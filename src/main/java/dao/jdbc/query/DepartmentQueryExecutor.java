package dao.jdbc.query;

import dao.jdbc.retrieve.DepartmentEntityRetriever;
import dao.jdbc.retrieve.EntityRetriever;
import dao.jdbc.supply.DepartmentValueSupplier;
import dao.jdbc.supply.ValueSupplier;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentQueryExecutor extends QueryExecutor<Department> {
    private EntityRetriever<Department> entityRetriever;
    private ValueSupplier<Department> valueSupplier;
    private DepartmentTableInfo tableInfo;

    private final String FIND_BY_NAME_QUERY =
            String.format("SELECT * FROM %s WHERE %s = ?;",
                    tableInfo.getTableName(),
                    tableInfo.getNameColumn());

    public DepartmentQueryExecutor() {
        entityRetriever = new DepartmentEntityRetriever();
        valueSupplier = new DepartmentValueSupplier();
        tableInfo = TableInfoFactory.getInstance().getDepartmentTableInfo();
    }

    public Optional<Department> prepareFindByName(Connection connection, String name)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return entityRetriever.retrieveEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected EntityRetriever<Department> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    protected ValueSupplier<Department> getValueSupplier() {
        return valueSupplier;
    }

    @Override
    protected DepartmentTableInfo getTableInfo() {
        return tableInfo;
    }
}
