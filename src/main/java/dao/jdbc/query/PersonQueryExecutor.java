package dao.jdbc.query;

import dao.metadata.PersonTableInfo;
import domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonQueryExecutor<E extends Person> extends QueryExecutor<E> {

    String getFindByFullNameQuery() {
        return String.format("SELECT * FROM %s WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                getTableInfo().getTableName(),
                getTableInfo().getNameColumn(),
                getTableInfo().getSurnameColumn());
    }

    public List<E> queryFindByFullName(Connection connection, String name, String surname)
            throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByFullNameQuery())) {
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet resultSet = statement.executeQuery();
            return getEntityRetriever().retrieveEntityList(resultSet);
        }
    }

    @Override
    protected abstract PersonTableInfo getTableInfo();
}
