package dao.jdbc.query.retrieve;

import dao.metadata.TableInfoFactory;
import domain.dto.*;

public class DtoRetrieverFactory {
    private DepartmentDtoRetriever departmentDtoRetriever;
    private DoctorDtoRetriever doctorDtoRetriever;
    private MedicDtoRetriever medicDtoRetriever;
    private PatientDtoRetriever patientDtoRetriever;
    private TherapyDtoRetriever therapyDtoRetriever;
    private CredentialsDtoRetriever credentialsDtoRetriever;

    private static final DtoRetrieverFactory INSTANCE = new DtoRetrieverFactory();

    private DtoRetrieverFactory() {
        TableInfoFactory tableInfoFactory = TableInfoFactory.getInstance();
        departmentDtoRetriever =
                new DepartmentDtoRetriever(tableInfoFactory.getDepartmentTableInfo());
        doctorDtoRetriever =
                new DoctorDtoRetriever(tableInfoFactory.getStuffTableInfo(),
                        tableInfoFactory.getDoctorTableInfo());
        medicDtoRetriever =
                new MedicDtoRetriever(tableInfoFactory.getStuffTableInfo(),
                        tableInfoFactory.getMedicTableInfo());
        patientDtoRetriever =
                new PatientDtoRetriever(tableInfoFactory.getPatientTableInfo());
        therapyDtoRetriever =
                new TherapyDtoRetriever(tableInfoFactory.getTherapyTableInfo());
        credentialsDtoRetriever =
                new CredentialsDtoRetriever(tableInfoFactory.getCredentialsTableInfo());
    }

    public static DtoRetrieverFactory getInstance() {
        return INSTANCE;
    }

    public DtoRetriever<DepartmentDTO> getDepartmentDtoRetriever() {
        return departmentDtoRetriever;
    }

    public DtoRetriever<DoctorDTO> getDoctorDtoRetriever() {
        return doctorDtoRetriever;
    }

    public DtoRetriever<MedicDTO> getMedicDtoRetriever() {
        return medicDtoRetriever;
    }

    public DtoRetriever<PatientDTO> getPatientDtoRetriever() {
        return patientDtoRetriever;
    }

    public DtoRetriever<TherapyDTO> getTherapyDtoRetriever() {
        return therapyDtoRetriever;
    }

    public DtoRetriever<CredentialsDTO> getCredentialsDtoRetriever() {
        return credentialsDtoRetriever;
    }
}
