package dao.jdbc;

import dao.CrudDao;
import domain.IdHolder;

import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<T extends IdHolder>
        implements CrudDao<T> {

    @Override
    public Optional<T> find(int id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public void create(T entity) {

    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void delete(int id) {

    }
}
