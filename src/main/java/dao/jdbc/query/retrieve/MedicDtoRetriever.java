package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;
import domain.dto.MedicDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicDtoRetriever extends AbstractDtoRetriever<MedicDTO> {
    private StuffTableInfo stuffTableInfo;

    MedicDtoRetriever(TableInfoFactory tableInfoFactory) {
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
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
                .build();
    }
}
