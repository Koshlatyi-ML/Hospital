package dao.jdbc;

import dao.TherapyDao;
import dao.jdbc.metadata.annotation.Entity;
import domain.model.Therapy;

import java.time.ZonedDateTime;
import java.util.List;

@Entity(Therapy.class)
public class TherapyJdbcDao extends CrudJdbcDao<Therapy> implements TherapyDao {

    @Override
    public List<Therapy> findByType(Therapy.Type type) {
        return null;
    }

    @Override
    public List<Therapy> findByAppointmentDateTime(ZonedDateTime dateTime) {
        return null;
    }

    @Override
    public List<Therapy> findByPatientId(long id) {
        return null;
    }

    @Override
    public List<Therapy> findByMedicId(long id) {
        return null;
    }

    @Override
    public List<Therapy> findByDoctorId(long id) {
        return null;
    }
}
