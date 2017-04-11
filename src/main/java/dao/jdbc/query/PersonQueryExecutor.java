package dao.jdbc.query;

import domain.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PersonQueryExecutor <T extends AbstractPersonDTO> {
    List<T> queryFindByFullName(Connection connection, String fullName) throws SQLException;
}
