package dao;

import domain.IdHolder;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends IdHolder> {
    Optional<T> find(long id);
    List<T> findAll();
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
    void delete(long id);
}
