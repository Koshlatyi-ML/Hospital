package dao.jdbc;

import dao.DaoFactory;
import dao.DaoManager;
import dao.TherapyDAO;
import dao.connection.ConnectionManager;
import dao.jdbc.query.QueryExecutor;
import dao.jdbc.query.QueryExecutorFactory;
import dao.jdbc.query.TherapyQueryExecutor;
import domain.Therapy;
import domain.dto.TherapyDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class TherapyJdbcDAO extends CrudJdbcDAO<TherapyDTO> implements TherapyDAO {
    private TherapyQueryExecutor queryExecutor;

    TherapyJdbcDAO(QueryExecutorFactory queryExecutorFactory,
                   ConnectionManager connectionManager) {
        queryExecutor = queryExecutorFactory.getTherapyQueryExecutor();
        this.connectionManager = connectionManager;
    }

    @Override
    public List<TherapyDTO> findCurrentByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findCurrentByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findCurrentByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindCurrentByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFinishedByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFinishedByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFinishedByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFinishedByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFutureByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFutureByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findFutureByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindFutureByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findByDoctorIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByDoctorIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findByMedicIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByMedicIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    public List<TherapyDTO> findByPatientIdAndType(long id, Therapy.Type type) {
        Connection connection = connectionManager.getConnection();
        if (connectionManager.isTransactional()) {
            try {
                return queryExecutor
                        .queryFindByPatientIdAndType(connection, id, type);
            } catch (SQLException e) {
                connectionManager.rollbackTransaction();
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
    protected QueryExecutor<TherapyDTO> getQueryExecutor() {
        return queryExecutor;
    }

    public static void main(String[] args) {
//        DaoManager daoManager = DaoManager.getInstance();
//        DaoFactory daoFactory = daoManager.getDaoFactory();
//        TherapyDAO therapyDAO = daoFactory.getTherapyDao();
//
//        TherapyDTO therapyDTO = new TherapyDTO.Builder()
//                .setTitle("headcutting")
////                .setType(TherapyDTO.Type.SURGERY_OPERATION.toString())
//                .setDescription("lol")
//                .setAppointmentDateTime(Timestamp.from(Instant.now()))
//                .setPatientId(4)
//                .setPerformerId(45)
//                .build();
//
//        therapyDAO.create(therapyDTO);
//
//        therapyDTO.setPerformerId(44);
//        therapyDAO.update(therapyDTO);
//        therapyDAO.delete(therapyDTO.getId());
    }
}
