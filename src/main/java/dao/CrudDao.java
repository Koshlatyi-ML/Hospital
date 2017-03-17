package dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(int id);
    List<T> findAll();
    void create(T entity);
    void update(T entity);
    void delete(int id);
}
