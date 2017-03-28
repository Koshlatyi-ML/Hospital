package dao.jdbc;

import dao.TherapyDao;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.jdbc.query.TherapyQueryExecutor;
import dao.metadata.annotation.mapping.Entity;
import domain.Therapy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Entity(Therapy.class)
public class TherapyJdbcDao extends CrudJdbcDao<Therapy> implements TherapyDao {
    private TherapyQueryExecutor queryExecutor;

    TherapyJdbcDao(QueryExecutorFactory queryExecutorFactory) {
        queryExecutor = queryExecutorFactory.getTherapyQueryExecutor();
    }

    @Override
    public List<Therapy> findCurrentByDoctorIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindCurrentByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findCurrentByMedicIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindCurrentByMedicIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findCurrentByPatientIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindCurrentByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFinishedByDoctorIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFinishedByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFinishedByMedicIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFinishedByMedicIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFinishedByPatientIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFinishedByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFutureByDoctorIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFutureByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFutureByMedicIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFutureByMedicIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findFutureByPatientIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindFutureByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findByDoctorIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByDoctorIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findByMedicIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByMedicIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Therapy> findByPatientIdAndType(long id, Therapy.Type type) {
        try (Connection connection = getConnection()) {
            return queryExecutor.queryFindByPatientIdAndType(connection, id, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected QueryExecutor<Therapy> getQueryExecutor() {
        return queryExecutor;
    }
}
