package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.TableInfoFactory;
import dao.metadata.TherapyTableInfo;
import domain.dto.TherapyDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TherapyDtoRetriever extends AbstractDtoRetriever<TherapyDTO> {
    private TherapyTableInfo tableInfo;

    TherapyDtoRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getTherapyTableInfo();
    }

    @Override
    protected TherapyDTO retrieve(ResultSet resultSet) throws SQLException {
        return new TherapyDTO.Builder()
                .setId(resultSet
                        .getLong(tableInfo.getIdColumn(ColumnNameStyle.FULL)))
                .setName(resultSet
                        .getString(tableInfo.getTitleColumn(ColumnNameStyle.FULL)))
                .setDescription(resultSet
                        .getString(tableInfo.getDescriptionColumn(ColumnNameStyle.FULL)))
                .setType(TherapyDTO.Type.valueOf(
                        resultSet.getString(tableInfo.getTypeColumn(ColumnNameStyle.FULL))))
                .setAppointmentDateTime(resultSet
                        .getTimestamp(tableInfo.getAppointmentDateColumn(ColumnNameStyle.FULL)))
                .setCompleteDateTime(resultSet
                        .getTimestamp(tableInfo.getCompleteDateColumn(ColumnNameStyle.FULL)))
                .setPatientId(resultSet
                        .getLong(tableInfo.getPatientIdColumn(ColumnNameStyle.FULL)))
                .setPerformerId(resultSet
                        .getLong(tableInfo.getPerformerIdColumn(ColumnNameStyle.FULL)))
                .build();
    }
}
