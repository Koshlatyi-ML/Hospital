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

        departmentQueryExecutor = new DepartmentQueryExecutor();
        departmentQueryExecutor.setTableInfo(tableInfoFactory.getDepartmentTableInfo());
        departmentQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getDepartmentDtoRetriever());
        departmentQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getDepartmentDtoValueSupplier());

        doctorQueryExecutor = new DoctorQueryExecutor();
        doctorQueryExecutor.setDoctorTableInfo(tableInfoFactory.getDoctorTableInfo());
        doctorQueryExecutor.setPatientTableInfo(tableInfoFactory.getPatientTableInfo());
        doctorQueryExecutor.setStuffTableInfo(tableInfoFactory.getStuffTableInfo());
        doctorQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getDoctorDtoRetriever());
        doctorQueryExecutor.setValueSupplier(valueSupplierFactory.getDoctorDtoValueSupplier());

        medicQueryExecutor = new MedicQueryExecutor();
        medicQueryExecutor.setMedicTableInfo(tableInfoFactory.getMedicTableInfo());
        medicQueryExecutor.setStuffTableInfo(tableInfoFactory.getStuffTableInfo());
        medicQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getMedicDtoRetriever());
        medicQueryExecutor.setValueSupplier(valueSupplierFactory.getMedicDtoValueSupplier());

        patientQueryExecutor = new PatientQueryExecutor();
        patientQueryExecutor.setTableInfo(tableInfoFactory.getPatientTableInfo());
        patientQueryExecutor.setDoctorTableInfo(tableInfoFactory.getDoctorTableInfo());
        patientQueryExecutor.setStuffTableInfo(tableInfoFactory.getStuffTableInfo());
        patientQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getPatientDtoRetriever());
        patientQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getPatientDtoValueSupplier());

        therapyQueryExecutor = new TherapyQueryExecutor();
        therapyQueryExecutor.setTableInfo(tableInfoFactory.getTherapyTableInfo());
        therapyQueryExecutor.setDoctorTableInfo(tableInfoFactory.getDoctorTableInfo());
        therapyQueryExecutor.setMedicTableInfo(tableInfoFactory.getMedicTableInfo());
        therapyQueryExecutor.setPatientTableInfo(tableInfoFactory.getPatientTableInfo());
        therapyQueryExecutor.setDtoRetriever(dtoRetrieverFactory.getTherapyEntityetriever());
        therapyQueryExecutor.setDtoValueSupplier(valueSupplierFactory.getTherapyDtoValueSupplier());
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
