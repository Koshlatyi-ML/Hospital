package dao.jdbc.query;

import domain.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DepartmentMemberQueryExecutor<T extends AbstractPersonDTO> {
    List<T> queryFindByDepartmentId(Connection connection, long id) throws SQLException;
}
