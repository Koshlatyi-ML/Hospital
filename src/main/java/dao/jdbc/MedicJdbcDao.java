package dao.jdbc;

import dao.MedicDao;
import dao.metadata.annotation.MedicAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Medic;

import java.util.List;
import java.util.Optional;

@Entity(Medic.class)
public class MedicJdbcDao implements MedicDao {

    @Override
    public Optional<Medic> find(int id) {
        return null;
    }

    @Override
    public List<Medic> findAll() {
        return null;
    }

    @Override
    public void create(Medic entity) {

    }

    @Override
    public void update(Medic entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
