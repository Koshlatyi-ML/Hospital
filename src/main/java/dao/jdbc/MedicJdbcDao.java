package dao.jdbc;

import dao.MedicDao;
import dao.metadata.annotation.MedicAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Medic;

import java.util.List;

@Entity(Medic.class)
public class MedicJdbcDao extends PersonJdbcDao<Medic, MedicAnnotTableInfo>
        implements MedicDao {

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
