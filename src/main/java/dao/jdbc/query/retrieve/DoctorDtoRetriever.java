package dao.jdbc.query.retrieve;

import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.dto.DoctorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDtoRetriever extends AbstractDtoRetriever<DoctorDTO> {
    private StuffTableInfo stuffTableInfo;

    DoctorDtoRetriever(TableInfoFactory tableInfoFactory) {
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
    }

    @Override
    protected DoctorDTO retrieve(ResultSet resultSet) throws SQLException {
        return new DoctorDTO.Builder()
                .setId(resultSet.getLong(stuffTableInfo.getIdColumn()))
                .setName(resultSet.getString(stuffTableInfo.getNameColumn()))
                .setSurname(resultSet.getString(stuffTableInfo.getSurnameColumn()))
                .setDepartmentId(resultSet.getLong(stuffTableInfo.getDepartmentIdColumn()))
                .build();
    }
}
