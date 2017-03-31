package dao.jdbc.query;

import dao.metadata.PersonTableInfo;
import domain.Person;
import domain.dto.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class PersonQueryExecutor<T extends AbstractPersonDTO> extends QueryExecutor<T> {

    String getFindByFullNameQuery() {
        return String.format("SELECT * FROM %s WHERE %s LIKE %%?%% OR %s LIKE %%?%%;",
                getTableInfo().getTableName(),
                getTableInfo().getNameColumn(),
                getTableInfo().getSurnameColumn());
    }

    public List<T> queryFindByFullName(Connection connection, String name, String surname)
            throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(getFindByFullNameQuery())) {
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet resultSet = statement.executeQuery();
            return getDtoRetriever().retrieveDTOList(resultSet);
        }
    }

    @Override
    protected abstract PersonTableInfo getTableInfo();
}
