package dao.jdbc;

import dao.CrudDao;
import dao.connection.ConnectionFactory;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryPreparer;
import dao.jdbc.retrieve.EntityRetriever;
import dao.jdbc.supply.ValueSupplier;
import dao.metadata.PlainTableInfo;
import domain.IdHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public abstract class CrudJdbcDao<E extends IdHolder,T extends PlainTableInfo>
        implements CrudDao<E> {

    protected ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    protected ThreadLocal<Connection> threadLocalConnection;
    protected T ownTableInfo;
    protected EntityRetriever<E> entityRetriever;
    protected ValueSupplier<E> valueSupplier;

    @Override
    public Optional<E> find(long id) {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement statement
                = QueryPreparer.prepareFindById(connection, ownTableInfo);
        ResultSet resultSet = QueryExecutor.executeIdBased(statement, id);
        return entityRetriever.retrieveEntity(resultSet);
    }

    @Override
    public List<E> findAll() {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement statement
                = QueryPreparer.prepareFindAll(connection, ownTableInfo);
        ResultSet resultSet = QueryExecutor.executeWithoutArgs(statement);
        return entityRetriever.retrieveEntityList(resultSet);
    }

    @Override
    public void create(E entity) {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement statement
                = QueryPreparer.prepareInsert(connection, ownTableInfo);
        QueryExecutor.executeAllColumnsBased(statement, valueSupplier, entity);
    }

    @Override
    public void update(E entity) {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement statement
                = QueryPreparer.prepareUpdate(connection, ownTableInfo);
        QueryExecutor.executeAllColumnsBased(statement, valueSupplier, entity);
    }

    @Override
    public void delete(long id) {
        Connection connection = connectionFactory.getConnection();
        PreparedStatement statement
                = QueryPreparer.prepareDelete(connection, ownTableInfo);
        QueryExecutor.executeIdBased(statement, id);
    }

    public Optional<Connection> getThreadLocalConnection() {
        return Optional.ofNullable(threadLocalConnection.get());
    }

    public void setThreadLocalConnection(Connection connection) {
        threadLocalConnection.set(connection);
    }

    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = null;
        Optional<Integer> optional = Optional.ofNullable(threadLocal.get());
    }

}
