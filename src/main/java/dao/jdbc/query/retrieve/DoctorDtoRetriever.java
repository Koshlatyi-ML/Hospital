package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.DoctorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDtoRetriever extends AbstractDtoRetriever<DoctorDTO> {
    private StuffTableInfo stuffTableInfo;
    private DoctorTableInfo doctorTableInfo;

    DoctorDtoRetriever(StuffTableInfo stuffTableInfo, DoctorTableInfo doctorTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
        this.doctorTableInfo = doctorTableInfo;
    }

    @Override
    protected DoctorDTO retrieve(ResultSet resultSet) throws SQLException {
        return new DoctorDTO.Builder()
                .setId(resultSet.getLong(stuffTableInfo
                        .getIdColumn(ColumnNameStyle.FULL)))
                .setName(resultSet.getString(stuffTableInfo
                        .getNameColumn(ColumnNameStyle.FULL)))
                .setSurname(resultSet.getString(stuffTableInfo
                        .getSurnameColumn(ColumnNameStyle.FULL)))
                .setDepartmentId(resultSet.getLong(stuffTableInfo
                        .getDepartmentIdColumn(ColumnNameStyle.FULL)))
                .setCredentialsId(resultSet.getLong(doctorTableInfo
                .getCredentialsIdColumn(ColumnNameStyle.FULL)))
                .build();
    }
}
