package service;

import domain.AbstractDTO;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends AbstractDTO> {
    Optional<T> get(long id);
    List<T> getAll(int offset, int limit);
    long getSize();
    void create(T t);
    void update(T t);
    void delete(long id);
}
