package dao.jdbc;

import dao.MedicDao;
import domain.model.Medic;

import java.util.List;
import java.util.Optional;

public class MedicJdbcDao extends PersonJdbcDao<Medic>
        implements MedicDao {

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
