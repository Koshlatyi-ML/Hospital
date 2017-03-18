package dao;

import domain.IdHolder;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends IdHolder> {
    Optional<T> find(int id);
    List<T> findAll();
    void create(T entity);
    void update(T entity);
    void delete(int id);
}
