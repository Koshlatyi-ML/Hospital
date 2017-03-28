package dao.jdbc.query;

import dao.jdbc.query.retrieve.EntityRetrieverFactory;
import dao.jdbc.query.supply.ValueSupplierFactory;
import dao.metadata.TableInfoFactory;

public class QueryExecutorFactory {
    private DepartmentQueryExecutor departmentQueryExecutor;
    private DoctorQueryExecutor doctorQueryExecutor;
    private MedicQueryExecutor medicQueryExecutor;
    private PatientQueryExecutor patientQueryExecutor;
    private TherapyQueryExecutor therapyQueryExecutor;

    private QueryExecutorFactory() {
        TableInfoFactory tableInfoFactory = TableInfoFactory.getInstance();
        EntityRetrieverFactory entityRetrieverFactory = EntityRetrieverFactory.getInstance();
        ValueSupplierFactory valueSupplierFactory = ValueSupplierFactory.getInstance();

        departmentQueryExecutor = new DepartmentQueryExecutor(tableInfoFactory,
                        valueSupplierFactory, entityRetrieverFactory);

        doctorQueryExecutor = new DoctorQueryExecutor(tableInfoFactory,
                valueSupplierFactory, entityRetrieverFactory);

        medicQueryExecutor = new MedicQueryExecutor(tableInfoFactory,
                valueSupplierFactory, entityRetrieverFactory);

        patientQueryExecutor = new PatientQueryExecutor(tableInfoFactory,
                valueSupplierFactory, entityRetrieverFactory);

        therapyQueryExecutor = new TherapyQueryExecutor(tableInfoFactory,
                valueSupplierFactory, entityRetrieverFactory);
    }

    private static final QueryExecutorFactory INSTANCE = new QueryExecutorFactory();

    public static QueryExecutorFactory getInstance() {
        return INSTANCE;
    }

    public DepartmentQueryExecutor getDepartmentQueryExecutor() {
        return departmentQueryExecutor;
    }

    public DoctorQueryExecutor getDoctorQueryExecutor() {
        return doctorQueryExecutor;
    }

    public MedicQueryExecutor getMedicQueryExecutor() {
        return medicQueryExecutor;
    }

    public PatientQueryExecutor getPatientQueryExecutor() {
        return patientQueryExecutor;
    }

    public TherapyQueryExecutor getTherapyQueryExecutor() {
        return therapyQueryExecutor;
    }
}
