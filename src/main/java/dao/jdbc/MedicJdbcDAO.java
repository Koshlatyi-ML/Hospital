package dao.jdbc;

import dao.MedicDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.MedicQueryExecutor;
import domain.dto.MedicDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MedicJdbcDAO extends StuffJdbcDAO<MedicDTO> implements MedicDAO {
    private MedicQueryExecutor queryExecutor;

    MedicJdbcDAO(MedicQueryExecutor queryExecutor,
                 ConnectionManager connectionManager) {

        this.queryExecutor = queryExecutor;
        this.connectionManager = connectionManager;
    }

    @Override
    protected MedicQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    @Override
    public Optional<MedicDTO> findByCredentialsId(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            return queryExecutor.queryFindByCredentialsId(connection, id);
        } catch (SQLException e) {
            connectionManager.tryRollback();
            throw new RuntimeException(e);
        }
    }
}
