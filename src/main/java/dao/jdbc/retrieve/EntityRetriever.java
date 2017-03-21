package dao.jdbc.retrieve;

import domain.IdHolder;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface EntityRetriever<T extends IdHolder> {
    Optional<T> retrieveEntity(ResultSet resultSet);
    List<T> retrieveEntityList(ResultSet resultSet);
}
