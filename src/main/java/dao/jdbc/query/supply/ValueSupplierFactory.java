package dao.jdbc.query.supply;

import domain.dto.*;

public class ValueSupplierFactory {
    private DepartmentDtoValueSupplier departmentDtoValueSupplier;
    private DoctorDtoValueSupplier doctorDtoValueSupplier;
    private MedicDtoValueSupplier medicDtoValueSupplier;
    private PatientDtoValueSupplier patientDtoValueSupplier;
    private TherapyDtoValueSupplier therapyDtoValueSupplier;
    private CredentialsDtoValueSupplier credentialsDtoValueSupplier;

    private static final ValueSupplierFactory INSTANCE = new ValueSupplierFactory();

    private ValueSupplierFactory() {
        departmentDtoValueSupplier = new DepartmentDtoValueSupplier();
        doctorDtoValueSupplier = new DoctorDtoValueSupplier();
        medicDtoValueSupplier = new MedicDtoValueSupplier();
        patientDtoValueSupplier = new PatientDtoValueSupplier();
        therapyDtoValueSupplier = new TherapyDtoValueSupplier();
        credentialsDtoValueSupplier = new CredentialsDtoValueSupplier();
    }

    public static ValueSupplierFactory getInstance() {
        return INSTANCE;
    }

    public DtoValueSupplier<DepartmentDTO> getDepartmentDtoValueSupplier() {
        return departmentDtoValueSupplier;
    }

    public StuffDtoValueSupplier<DoctorDTO> getDoctorDtoValueSupplier() {
        return doctorDtoValueSupplier;
    }

    public StuffDtoValueSupplier<MedicDTO> getMedicDtoValueSupplier() {
        return medicDtoValueSupplier;
    }

    public DtoValueSupplier<PatientDTO> getPatientDtoValueSupplier() {
        return patientDtoValueSupplier;
    }

    public DtoValueSupplier<TherapyDTO> getTherapyDtoValueSupplier() {
        return therapyDtoValueSupplier;
    }

    public DtoValueSupplier<CredentialsDTO> getCredentialsValueSupplier() {
        return credentialsDtoValueSupplier;
    }
}
