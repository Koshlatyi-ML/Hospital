package dao.jdbc;

import dao.DaoFactory;
import dao.MedicDao;
import dao.jdbc.query.MedicQueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.metadata.annotation.mapping.Entity;
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
        try (Connection connection = getConnection()) {
            Optional<Medic> medicOptional =
                    queryExecutor.queryFindById(connection, id);
            medicOptional.ifPresent(this::setTherapies);
            return medicOptional;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Medic> findAll() {
        try (Connection connection = getConnection()) {
            List<Medic> medicList = queryExecutor.queryFindAll(connection);
            setTherapies(medicList);
            return medicList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Medic> findByFullName(String name, String surname) {
        try (Connection connection = getConnection()) {
            List<Medic> medicList =
                    queryExecutor.queryFindByFullName(connection, name, surname);
            setTherapies(medicList);
            return medicList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Medic> findByDepartmentId(long id) {
        try (Connection connection = getConnection()) {
            List<Medic> medicList =
                    queryExecutor.queryFindByDepartmentId(connection, id);
            setTherapies(medicList);
            return medicList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
