package dao.jdbc.query;

import domain.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface CredentialsHolderQueryExecutor<T extends AbstractPersonDTO> {
    Optional<T> queryFindByCredentialsId(Connection connection, long id) throws SQLException;
}
