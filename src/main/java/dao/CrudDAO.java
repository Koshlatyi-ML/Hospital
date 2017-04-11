package dao;

import domain.AbstractDTO;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T extends AbstractDTO> {
    Optional<T> find(long id);
    List<T> findAll();
    void create(T dto);
    void update(T dto);
    void delete(T dto);
    void delete(long id);
}
