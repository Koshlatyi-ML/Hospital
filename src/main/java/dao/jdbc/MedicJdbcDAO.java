package dao.jdbc;

import dao.MedicDAO;
import dao.jdbc.query.MedicQueryExecutor;
import domain.MedicDTO;

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
}
