package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DoctorTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.dto.DoctorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDtoRetriever extends AbstractDtoRetriever<DoctorDTO> {
    private StuffTableInfo stuffTableInfo;

    DoctorDtoRetriever(StuffTableInfo stuffTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
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
                .build();
    }
}
