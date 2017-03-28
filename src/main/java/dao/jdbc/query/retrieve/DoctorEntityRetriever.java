package dao.jdbc.query.retrieve;

import dao.metadata.DoctorTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorEntityRetriever extends AbstractEntityRetriever<Doctor> {
    private DoctorTableInfo tableInfo;

    DoctorEntityRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getDoctorTableInfo();
    }

    @Override
    protected Doctor retrieve(ResultSet resultSet) throws SQLException {
        return new Doctor.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .setSurname(resultSet.getString(tableInfo.getSurnameColumn()))
                .build();
    }
}
