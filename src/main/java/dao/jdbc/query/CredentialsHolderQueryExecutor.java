package dao.jdbc.query;

import domain.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface CredentialsHolderQueryExecutor<T extends AbstractPersonDTO> {
    Optional<T> queryFindByLoginAndPassword(
            Connection connection, String login, String password) throws SQLException;

    Optional<T> queryFindByCredentialsId(Connection connection, long id) throws SQLException;
}
