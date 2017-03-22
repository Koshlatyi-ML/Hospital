package dao.jdbc;

import dao.CrudDao;
import dao.MedicDao;
import dao.metadata.MedicTableInfo;
import dao.metadata.annotation.MedicAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Medic;

import java.util.List;
import java.util.Optional;

@Entity(Medic.class)
public class MedicJdbcDao extends CrudJdbcDao<Medic, MedicTableInfo>
        implements MedicDao {

    @Override
    public Optional<Medic> find(long id) {
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
    public void delete(long id) {

    }

    @Override
    public Optional<List<Medic>> findBySurname(String surname) {
        return null;
    }

    @Override
    public List<Medic> findByFullName(String name, String surname) {
        return null;
    }

    @Override
    public List<Medic> findByDepartmentId(long id) {
        return null;
    }
}
