package dao.jdbc;

import dao.MedicDAO;
import dao.connection.jdbc.ConnectionManager;
import dao.jdbc.query.MedicQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.metadata.annotation.mapping.Entity;
import domain.Medic;
import domain.Therapy;
import domain.dto.MedicDTO;

import java.util.List;
import java.util.Optional;

@Entity(Medic.class)
public class MedicJdbcDAO extends StuffJdbcDAO<MedicDTO> implements MedicDAO {
    private MedicQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    MedicJdbcDAO(QueryExecutorFactory queryExecutorFactory,
                 JdbcDaoFactory jdbcDaoFactory,
                 ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutorFactory.getMedicQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
        this.connectionManager = connectionManager;
    }

    @Override
    protected MedicQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }
}
