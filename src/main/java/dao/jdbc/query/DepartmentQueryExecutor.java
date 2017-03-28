package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.ValueSupplier;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentQueryExecutor extends QueryExecutor<Department> {
    private DepartmentTableInfo tableInfo;
    private ValueSupplier<Department> valueSupplier;
    private EntityRetriever<Department> entityRetriever;

    DepartmentQueryExecutor(TableInfoFactory tableInfoFactory,
                                   ValueSupplierFactory valueSupplierFactory,
                                   EntityRetrieverFactory entityRetrieverFactory) {

        entityRetriever = entityRetrieverFactory.getDepartmentEntityRetriever();
        valueSupplier = valueSupplierFactory.getDepartamentValueSupplier();
        tableInfo = tableInfoFactory.getDepartmentTableInfo();
    }

    private String getFindByNameQuery() {
        return String.format("SELECT * FROM %s WHERE %s = ?;",
                tableInfo.getTableName(),
                tableInfo.getNameColumn());
    }

    public Optional<Department> queryFindByName(Connection connection, String name)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByNameQuery())) {
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
