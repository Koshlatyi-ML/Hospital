package dao.jdbc.query.retrieve;

import dao.metadata.DoctorTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorEntityRetriever extends AbstractEntityRetriever<Doctor> {
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;

    DoctorEntityRetriever(TableInfoFactory tableInfoFactory) {
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
        doctorTableInfo = tableInfoFactory.getDoctorTableInfo();
    }

    @Override
    protected Doctor retrieve(ResultSet resultSet) throws SQLException {
        return new Doctor.Builder()
                .setId(resultSet.getLong(stuffTableInfo.getIdColumn()))
                .setName(resultSet.getString(stuffTableInfo.getNameColumn()))
                .setSurname(resultSet.getString(stuffTableInfo.getSurnameColumn()))
                .build();
    }
}
