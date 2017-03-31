package dao.jdbc.query.retrieve;

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
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .setDescription(resultSet.getString(tableInfo.getDescriptionColumn()))
                .setType(TherapyDTO.Type.valueOf(
                        resultSet.getString(tableInfo.getTypeColumn())))
                .setAppointmentDateTime(
                        resultSet.getTimestamp(tableInfo.getAppointmentDateColumn())
                                .toInstant())
                .setCompleteDateTime(
                        resultSet.getTimestamp(tableInfo.getCompleteDateColumn())
                                .toInstant())
                .setPatientId(resultSet.getLong(tableInfo.getPatientIdColumn()))
                .setPerfomerId(resultSet.getLong(tableInfo.getPerformerIdColumn()))
                .build();
    }
}
