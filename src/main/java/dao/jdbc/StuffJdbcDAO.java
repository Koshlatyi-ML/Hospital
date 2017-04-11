package dao.jdbc;

import dao.jdbc.query.StuffQueryExecutor;
import domain.AbstractStuffDTO;

import java.util.List;

public abstract class StuffJdbcDAO<T extends AbstractStuffDTO> extends CrudJdbcDAO<T> {

    @Override
    public void create(T dto) {
        connectionManager.beginTransaction();
        super.create(dto);
        connectionManager.finishTransaction();
    }

    @Override
    public void update(T dto) {
        connectionManager.beginTransaction();
        super.update(dto);
        connectionManager.finishTransaction();
    }

    public List<T> findByFullName(String fullName) {
        return JdbcDaoCommons.findByFullName(connectionManager, getQueryExecutor(), fullName);
    }

    public List<T> findByDepartmentId(long id) {
        return JdbcDaoCommons.findByDepartmentId(connectionManager, getQueryExecutor(), id);
    }

    @Override
    protected abstract StuffQueryExecutor<T> getQueryExecutor();
}
