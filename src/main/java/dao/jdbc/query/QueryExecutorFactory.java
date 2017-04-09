package dao.jdbc.query;

import dao.jdbc.query.retrieve.DtoRetrieverFactory;
import dao.jdbc.query.supply.ValueSupplierFactory;

public class QueryExecutorFactory {

    private DepartmentQueryExecutor departmentQueryExecutor;
    private DoctorQueryExecutor doctorQueryExecutor;
    private MedicQueryExecutor medicQueryExecutor;
    private PatientQueryExecutor patientQueryExecutor;
    private TherapyQueryExecutor therapyQueryExecutor;
    private CredentialsQueryExecutor credentialsQueryExecutor;

    private QueryExecutorFactory() {
        DtoRetrieverFactory dtoRetrieverFactory = DtoRetrieverFactory.getInstance();
        ValueSupplierFactory valueSupplierFactory = ValueSupplierFactory.getInstance();

        departmentQueryExecutor = new DepartmentQueryExecutor();
        departmentQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getDepartmentDtoRetriever());
        departmentQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getDepartmentDtoValueSupplier());

        doctorQueryExecutor = new DoctorQueryExecutor();
        doctorQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getDoctorDtoRetriever());
        doctorQueryExecutor.setValueSupplier(valueSupplierFactory.getDoctorDtoValueSupplier());

        medicQueryExecutor = new MedicQueryExecutor();
        medicQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getMedicDtoRetriever());
        medicQueryExecutor.setValueSupplier(valueSupplierFactory.getMedicDtoValueSupplier());

        patientQueryExecutor = new PatientQueryExecutor();
        patientQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getPatientDtoRetriever());
        patientQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getPatientDtoValueSupplier());

        therapyQueryExecutor = new TherapyQueryExecutor();
        therapyQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getTherapyDtoRetriever());
        therapyQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getTherapyDtoValueSupplier());

        credentialsQueryExecutor = new CredentialsQueryExecutor();
        credentialsQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getCredentialsDtoRetriever());
        credentialsQueryExecutor.setValueSupplier(valueSupplierFactory.getCredentialsValueSupplier());
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

    public CredentialsQueryExecutor getCredentialsQueryExecutor() {
        return credentialsQueryExecutor;
    }
}
