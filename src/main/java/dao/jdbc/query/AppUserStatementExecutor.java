package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import domain.dto.AbstractDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class AppUserStatementExecutor {

    private AppUserStatementExecutor() {
    }

    static <T extends AbstractDTO> Optional<T> queryFindByCredentialsId(
            Connection connection, long id, String sqlQuery, DtoRetriever<T> dtoRetriever)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(sqlQuery)) {

            statement.setLong(1, id);
            return dtoRetriever.retrieveDTO(statement.executeQuery());
        }
    }
}
