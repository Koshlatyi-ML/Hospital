package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import domain.dto.MedicDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicDtoRetriever extends AbstractDtoRetriever<MedicDTO> {
    private StuffTableInfo stuffTableInfo;
    private MedicTableInfo medicTableInfo;

    MedicDtoRetriever(StuffTableInfo stuffTableInfo, MedicTableInfo medicTableInfo) {
        this.stuffTableInfo = stuffTableInfo;
        this.medicTableInfo = medicTableInfo;
    }

    @Override
    protected MedicDTO retrieve(ResultSet resultSet) throws SQLException {
        return new MedicDTO.Builder()
                .setId(resultSet.getLong(stuffTableInfo
                        .getIdColumn(ColumnNameStyle.FULL)))
                .setName(resultSet.getString(stuffTableInfo
                        .getNameColumn(ColumnNameStyle.FULL)))
                .setSurname(resultSet.getString(stuffTableInfo
                        .getSurnameColumn(ColumnNameStyle.FULL)))
                .setDepartmentId(resultSet.getLong(stuffTableInfo
                        .getDepartmentIdColumn(ColumnNameStyle.FULL)))
                .setCredentialsId(resultSet.getLong(medicTableInfo
                        .getCredentialsIdColumn(ColumnNameStyle.FULL)))
                .build();
    }
}
