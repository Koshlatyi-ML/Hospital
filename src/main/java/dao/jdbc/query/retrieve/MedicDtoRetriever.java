package dao.jdbc.query.retrieve;

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
                .setId(resultSet.getLong(stuffTableInfo.getIdColumn()))
                .setName(resultSet.getString(stuffTableInfo.getNameColumn()))
                .setSurname(resultSet.getString(stuffTableInfo.getSurnameColumn()))
                .setDepartmentId(resultSet.getLong(stuffTableInfo.getDepartmentIdColumn()))
                .build();
    }
}
