package dao.jdbc.query.retrieve;

import domain.dto.AbstractDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DtoRetriever<T extends AbstractDTO> {
    Optional<T> retrieveDTO(ResultSet resultSet) throws SQLException;
    List<T> retrieveDtoList(ResultSet resultSet) throws SQLException;
}
