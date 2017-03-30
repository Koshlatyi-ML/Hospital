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
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindCurrentByDoctorIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findCurrentByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindCurrentByMedicIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findCurrentByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindCurrentByPatientIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFinishedByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFinishedByDoctorIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFinishedByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFinishedByMedicIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFinishedByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFinishedByPatientIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFutureByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFutureByDoctorIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFutureByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFutureByMedicIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findFutureByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindFutureByPatientIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindByDoctorIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindByMedicIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Therapy> findByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackAndClose(connection);
                throw new RuntimeException(e);
            }
        } else {
            try (Connection localConnection = connection) {
                return queryExecutor
                        .queryFindByPatientIdAndType(localConnection, id, type);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected QueryExecutor<Therapy> getQueryExecutor() {
        return queryExecutor;
    }
}
