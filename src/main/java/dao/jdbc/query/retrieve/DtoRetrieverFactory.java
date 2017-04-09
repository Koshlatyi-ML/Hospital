package dao.jdbc.query.retrieve;

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
        departmentDtoRetriever = new DepartmentDtoRetriever();
        doctorDtoRetriever = new DoctorDtoRetriever();
        medicDtoRetriever = new MedicDtoRetriever();
        patientDtoRetriever = new PatientDtoRetriever();
        therapyDtoRetriever = new TherapyDtoRetriever();
        credentialsDtoRetriever = new CredentialsDtoRetriever();
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
