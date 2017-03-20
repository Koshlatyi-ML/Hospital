package dao.jdbc;

import dao.TherapyDao;
import dao.metadata.TherapyTableInfo;
import dao.metadata.annotation.TherapyAnnotTableInfo;
import dao.metadata.annotation.mapping.Entity;
import domain.Therapy;

import java.time.ZonedDateTime;
import java.util.List;

@Entity(Therapy.class)
public class TherapyJdbcDao extends CrudJdbcDao<Therapy, TherapyTableInfo>
                            implements TherapyDao {

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
