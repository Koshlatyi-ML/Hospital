package dao.jdbc.query.retrieve;

import domain.AbstractDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDtoRetriever<T extends AbstractDTO> implements DtoRetriever<T> {
    @Override
    public Optional<T> retrieveDTO(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(retrieve(resultSet));
    }

    @Override
    public List<T> retrieveDtoList(ResultSet resultSet) throws SQLException {
        List<T> dtoList = new ArrayList<>();
        while (resultSet.next()) {
            T dto = retrieve(resultSet);
            dtoList.add(dto);
        }
        return dtoList;
    }

    protected abstract T retrieve(ResultSet resultSet) throws SQLException;
}
