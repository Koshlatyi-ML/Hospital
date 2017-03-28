package dao.jdbc.query.retrieve;

import dao.metadata.MedicTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicEntityRetriever extends AbstractEntityRetriever<Medic> {
    private MedicTableInfo tableInfo;

    MedicEntityRetriever(TableInfoFactory tableInfoFactory) {
        tableInfo = tableInfoFactory.getMedicTableInfo();
    }

    @Override
    protected Medic retrieve(ResultSet resultSet) throws SQLException {
        return new Medic.Builder()
                .setId(resultSet.getLong(tableInfo.getIdColumn()))
                .setName(resultSet.getString(tableInfo.getNameColumn()))
                .setSurname(resultSet.getString(tableInfo.getSurnameColumn()))
                .build();
    }
}
