package dao.metadata;

import dao.metadata.constant.ConstantTableInfoFactory;
import util.load.Implementation;
import util.load.ImplementationLoader;
import util.load.ImplementationLoaderFactory;

@Implementation(ConstantTableInfoFactory.class)
public abstract class TableInfoFactory {
    private static final TableInfoFactory INSTANCE = ImplementationLoaderFactory.getInstance()
            .createImplementationLoader().loadInstance(TableInfoFactory.class);

    public static TableInfoFactory getInstance() {
        return INSTANCE;
    }

    public abstract DepartmentTableInfo getDepartmentTableInfo();
    public abstract StuffTableInfo getStuffTableInfo();
    public abstract DoctorTableInfo getDoctorTableInfo();
    public abstract MedicTableInfo getMedicTableInfo();
    public abstract PatientTableInfo getPatientTableInfo();
    public abstract TherapyTableInfo getTherapyTableInfo();
    public abstract CredentialsTableInfo getCredentialsTableInfo();
}
