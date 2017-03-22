package dao.jdbc.query;

import dao.metadata.DoctorTableInfo;
import dao.metadata.PatientTableInfo;
import dao.metadata.StuffTableInfo;
import dao.metadata.TableInfoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorQueryPreparer extends QueryPreparer<DoctorTableInfo> {
    private StuffTableInfo stuffTableInfo
            = TableInfoFactory.getInstance().getStuffTableInfo();
    private PatientTableInfo patientTableInfo
            = TableInfoFactory.getInstance().getPatientTableInfo();

    public PreparedStatement prepareFindByPatient(Connection connection) throws SQLException {
        return connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = ?",
                        stuffTableInfo.getTableName(),
                        stuffTableInfo.getIdColumnName(),
                        patientTableInfo.getDoctorIdColumn()));
    }
}
