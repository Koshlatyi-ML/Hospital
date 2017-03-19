package dao.jdbc;

import dao.MedicDao;
import dao.metadata.MedicTableInfo;
import dao.metadata.annotation.Entity;
import domain.Medic;

import java.util.List;

@Entity(Medic.class)
public class MedicJdbcDao extends PersonJdbcDao<Medic, MedicTableInfo>
        implements MedicDao {

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
