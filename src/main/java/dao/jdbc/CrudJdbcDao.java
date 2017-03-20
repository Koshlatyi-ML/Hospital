package dao.jdbc;

import dao.CrudDao;
import dao.metadata.PlainTableInfo;
import domain.IdHolder;

import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<E extends IdHolder,
                                  T extends PlainTableInfo> implements CrudDao<E> {
    protected T ownTableInfo;
    @Override
    public Optional<E> find(int id) {
        return null;
    }

    @Override
    public List<E> findAll() {
        return null;
    }

    @Override
    public void create(E entity) {

    }

    @Override
    public void update(E entity) {

    }

    @Override
    public void delete(int id) {

    }
}
