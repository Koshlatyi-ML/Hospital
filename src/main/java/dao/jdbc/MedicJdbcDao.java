package dao.jdbc;

import dao.MedicDao;
import dao.jdbc.metadata.annotation.Entity;
import domain.model.Medic;

import java.util.List;

@Entity(Medic.class)
public class MedicJdbcDao extends PersonJdbcDao<Medic>
        implements MedicDao {

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
