package dao.jdbc.query.retrieve;

import dao.metadata.ColumnNameStyle;
import dao.metadata.DepartmentTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Department;
import domain.dto.DepartmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDtoRetriever extends AbstractDtoRetriever<DepartmentDTO> {
    private DepartmentTableInfo tableInfo;

    DepartmentDtoRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getDepartmentTableInfo();
    }

    @Override
    public DepartmentDTO retrieve(ResultSet resultSet) throws SQLException{
        return new DepartmentDTO.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn(ColumnNameStyle.FULL)))
                .setName(resultSet.getString(tableInfo.getNameColumn(ColumnNameStyle.FULL)))
                .build();
    }
}
