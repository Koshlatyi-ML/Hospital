package dao.jdbc;

import dao.MedicDao;
import dao.jdbc.query.MedicQueryExecutor;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.StuffQueryExecutor;
import dao.jdbc.query.retrieve.MedicEntityRetriever;
import dao.jdbc.query.supply.MedicValueSupplier;
import dao.metadata.MedicTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Medic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Entity(Medic.class)
public class MedicJdbcDao extends StuffJdbcDao<Medic> implements MedicDao {
    private MedicQueryExecutor queryExecutor;
    private TherapyJdbcDao therapyDao;

    public MedicJdbcDao(TherapyJdbcDao therapyDao) {
        this.therapyDao = therapyDao;
        queryExecutor = new MedicQueryExecutor();
    }

    @Override
    public Optional<Medic> find(long id) {
        Optional<Medic> medicOptional;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            medicOptional = super.find(id);
            medicOptional.ifPresent(medic -> {
                setTherapies(connection, medic);
            });
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }
        return medicOptional;
    }

    @Override
    public List<Medic> findAll() {
        List<Medic> medicList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            medicList = super.findAll();
            setTherapies(connection, medicList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }

        return medicList;
    }

    @Override
    public List<Medic> findByFullName(String name, String surname) {
        List<Medic> medicList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            medicList = super.findByFullName(name, surname);
            setTherapies(connection, medicList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }
        return medicList;
    }

    @Override
    public List<Medic> findByDepartmentId(long id) {
        List<Medic> medicList;
        try (Connection connection = getConnection()) {
            setThreadLocalConnection(connection);

            medicList = super.findByDepartmentId(id);
            setTherapies(connection, medicList);
        } catch (SQLException e) {
            releaseThreadLocalConnection();
            throw new RuntimeException(e);
        }
        return medicList;
    }

    @Override
    protected MedicQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    private void setTherapies(Connection connection, Medic medic) {
        long medicId = medic.getId();

        therapyDao.setThreadLocalConnection(connection);
        medic.setPharmacotherapies(therapyDao.findPharmacotherapiesByDoctorId(medicId));
        medic.setPhysiotherapies(therapyDao.findPhysiotherapiesByDoctorId(medicId));
        therapyDao.releaseThreadLocalConnection();
    }

    private void setTherapies(Connection connection, List<Medic> medics) {
        therapyDao.setThreadLocalConnection(connection);
        medics.forEach(medic -> {
            long doctorId = medic.getId();
            medic.setPharmacotherapies(therapyDao.findPharmacotherapiesByDoctorId(doctorId));
            medic.setPhysiotherapies(therapyDao.findPhysiotherapiesByDoctorId(doctorId));
        });
        therapyDao.releaseThreadLocalConnection();
    }
}
