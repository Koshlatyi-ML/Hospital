package service;

import dao.CrudDAO;
import domain.AbstractDTO;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T extends AbstractDTO> implements CrudService<T> {

    @Override
    public Optional<T> get(long id) {
        return getDAO().find(id);
    }

    @Override
    public List<T> getAll(int offset, int limit) {
        return getDAO().findAll(offset, limit);
    }

    @Override
    public long getSize() {
        return getDAO().count();
    }

    @Override
    public void create(T t) {
        getDAO().create(t);
    }

    @Override
    public void update(T t) {
        getDAO().update(t);
    }

    @Override
    public void delete(long id) {
        getDAO().delete(id);
    }

    abstract CrudDAO<T> getDAO();
}
