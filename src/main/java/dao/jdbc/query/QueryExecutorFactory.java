package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetrieverFactory;
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
        DtoRetrieverFactory dtoRetrieverFactory = DtoRetrieverFactory.getInstance();
        ValueSupplierFactory valueSupplierFactory = ValueSupplierFactory.getInstance();

        departmentQueryExecutor = new DepartmentQueryExecutor(tableInfoFactory,
                        valueSupplierFactory, dtoRetrieverFactory);

        doctorQueryExecutor = new DoctorQueryExecutor(tableInfoFactory,
                valueSupplierFactory, dtoRetrieverFactory);

        medicQueryExecutor = new MedicQueryExecutor(tableInfoFactory,
                valueSupplierFactory, dtoRetrieverFactory);

        patientQueryExecutor = new PatientQueryExecutor(tableInfoFactory,
                valueSupplierFactory, dtoRetrieverFactory);

        therapyQueryExecutor = new TherapyQueryExecutor(tableInfoFactory,
                valueSupplierFactory, dtoRetrieverFactory);
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
