package dao.jdbc;

import dao.MedicDao;
import dao.jdbc.query.MedicQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.metadata.annotation.mapping.Entity;
import domain.Doctor;
import domain.Medic;
import domain.Therapy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Entity(Medic.class)
public class MedicJdbcDao extends StuffJdbcDao<Medic> implements MedicDao {
    private MedicQueryExecutor queryExecutor;
    private JdbcDaoFactory jdbcDaoFactory;

    MedicJdbcDao(QueryExecutorFactory queryExecutorFactory,
                 JdbcDaoFactory jdbcDaoFactory) {

        this.queryExecutor = queryExecutorFactory.getMedicQueryExecutor();
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    @Override
    public Optional<Medic> find(long id) {
        Optional<Medic> medicOptional = super.find(id);
        medicOptional.ifPresent(medic -> setTherapies(medic));
        return medicOptional;
    }

    @Override
    public List<Medic> findAll() {
        List<Medic> medicList = super.findAll();
        setTherapies(medicList);
        return medicList;
    }

    @Override
    public List<Medic> findByFullName(String name, String surname) {
        List<Medic> medicList = super.findByFullName(name, surname);
        setTherapies(medicList);
        return medicList;
    }

    @Override
    public List<Medic> findByDepartmentId(long id) {
        List<Medic> medicList = super.findByDepartmentId(id);
        setTherapies(medicList);
        return medicList;
    }
    @Override
    protected MedicQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    private void setTherapies(Medic medic) {
        setTherapies(jdbcDaoFactory.getTherapyDao(), medic);
    }

    private void setTherapies(List<Medic> medics) {
        TherapyJdbcDao therapyJdbcDao = jdbcDaoFactory.getTherapyDao();
        medics.forEach(medic -> setTherapies(therapyJdbcDao, medic));
    }

    private void setTherapies(TherapyJdbcDao therapyJdbcDao, Medic medic) {
        long medicId = medic.getId();
        medic.setPharmacotherapies(therapyJdbcDao.findByMedicIdAndType(medicId,
                Therapy.Type.PHARMACOTHERAPY));
        medic.setPhysiotherapies(therapyJdbcDao.findByMedicIdAndType(medicId,
                Therapy.Type.PHYSIOTHERAPY));
    }
}
