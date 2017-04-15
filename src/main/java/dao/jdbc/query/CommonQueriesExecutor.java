package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetriever;
import domain.AbstractDTO;
import domain.AbstractPersonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class CommonQueriesExecutor {

    private CommonQueriesExecutor() {
    }

    static <T extends AbstractDTO> Optional<T> findByLoginAndPassword(
            Connection connection, String login, String password,
            String sqlQuery, DtoRetriever<T> dtoRetriever)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, login);
            statement.setString(2, password);
            return dtoRetriever.retrieveDTO(statement.executeQuery());
        }
    }

    static <T extends AbstractDTO> Optional<T> findByCredentialsId(
            Connection connection, long id, String sqlQuery, DtoRetriever<T> dtoRetriever)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            return dtoRetriever.retrieveDTO(statement.executeQuery());
        }
    }

    static <T extends AbstractPersonDTO> List<T> findByFullName(
            Connection connection, String fullName, String sqlQuery,
            DtoRetriever<T> dtoRetriever) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, "%" + fullName + "%");
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }

    static <T extends AbstractDTO> List<T> findByDepartmentId(
            Connection connection, long id, String sqlQuery, DtoRetriever<T> dtoRetriever)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return dtoRetriever.retrieveDtoList(resultSet);
        }
    }
}
