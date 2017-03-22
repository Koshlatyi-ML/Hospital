package dao.jdbc.retrieve;

import domain.IdHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityRetriever<T extends IdHolder> implements EntityRetriever<T> {
    @Override
    public Optional<T> retrieveEntity(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(retrieve(resultSet));
    }

    @Override
    public List<T> retrieveEntityList(ResultSet resultSet) throws SQLException {
        List<T> entityList = new ArrayList<>();
        while (resultSet.next()) {
            T entity = retrieve(resultSet);
            entityList.add(entity);
        }
        return entityList;
    }

    protected abstract T retrieve(ResultSet resultSet) throws SQLException;
}
