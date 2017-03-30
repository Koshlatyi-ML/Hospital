package dao.jdbc.query.retrieve;

import dao.metadata.MedicTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;
import domain.Medic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicEntityRetriever extends AbstractEntityRetriever<Medic> {
    private MedicTableInfo medicTableInfo;
    private StuffTableInfo stuffTableInfo;

    MedicEntityRetriever(TableInfoFactory tableInfoFactory) {
        medicTableInfo = tableInfoFactory.getMedicTableInfo();
        stuffTableInfo = tableInfoFactory.getStuffTableInfo();
    }

    @Override
    protected Medic retrieve(ResultSet resultSet) throws SQLException {
        return new Medic.Builder()
                .setId(resultSet.getLong(stuffTableInfo.getIdColumn()))
                .setName(resultSet.getString(stuffTableInfo.getNameColumn()))
                .setSurname(resultSet.getString(stuffTableInfo.getSurnameColumn()))
                .build();
    }
}
