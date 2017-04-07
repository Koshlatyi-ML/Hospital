package dao.jdbc;

import dao.DaoManager;
import dao.MedicDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.MedicQueryExecutor;
import domain.dto.MedicDTO;

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

    public static void main(String[] args) {
        DaoManager daoManager = DaoManager.getInstance();
//        DaoFactory daoFactory = daoManager.getDaoFactory();
//        MedicDAO medicDAO = daoFactory.getMedicDao();
        MedicDTO dto = new MedicDTO.Builder()
                .setName("Hugh")
                .setSurname("Laurie")
                .setDepartmentId(94)
                .build();
//        medicDAO.create(dto);

        dto.setName("Liza");
        dto.setSurname("Edelstein");
//        medicDAO.update(dto);

//        medicDAO.delete(dto);

    }
}
