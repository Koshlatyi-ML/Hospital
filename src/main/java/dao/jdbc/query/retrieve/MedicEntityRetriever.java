package dao.jdbc.query.retrieve;

import domain.Medic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicEntityRetriever extends AbstractEntityRetriever<Medic> {
    @Override
    protected Medic retrieve(ResultSet resultSet) throws SQLException {
        return new Medic.Builder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setSurname(resultSet.getString(3))
                .build();
    }
}
