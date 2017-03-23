package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetriever;
import dao.jdbc.query.retrieve.PatientEntityRetriever;
import dao.jdbc.query.supply.PatientValueSupplier;
import dao.jdbc.query.supply.ValueSupplier;
import dao.metadata.PatientTableInfo;
import domain.Patient;

public class PatientQueryExecutor extends PersonQueryExecutor<Patient> {
    private PatientTableInfo tableInfo;
    private PatientEntityRetriever entityRetriever;
    private PatientValueSupplier valueSupplier;

    @Override
    protected PatientTableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    protected EntityRetriever<Patient> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    protected ValueSupplier<Patient> getValueSupplier() {
        return valueSupplier;
    }
}
