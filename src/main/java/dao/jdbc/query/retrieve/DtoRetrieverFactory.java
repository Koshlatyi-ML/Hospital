package dao.jdbc.query.retrieve;

import dao.metadata.TableInfoFactory;
import domain.dto.*;

public class DtoRetrieverFactory {
    private DepartmentDtoRetriever departmentDtoRetriever;
    private DoctorDtoRetriever doctorDtoRetriever;
    private MedicDtoRetriever medicDtoRetriever;
    private PatientDtoRetriever patientDtoRetriever;
    private TherapyDtoRetriever therapyDtoRetriever;

    private static final DtoRetrieverFactory INSTANCE = new DtoRetrieverFactory();

    private DtoRetrieverFactory() {
        TableInfoFactory tableInfoFactory = TableInfoFactory.getInstance();
        departmentDtoRetriever = new DepartmentDtoRetriever(tableInfoFactory);
        doctorDtoRetriever = new DoctorDtoRetriever(tableInfoFactory);
        medicDtoRetriever = new MedicDtoRetriever(tableInfoFactory);
        patientDtoRetriever = new PatientDtoRetriever(tableInfoFactory);
        therapyDtoRetriever = new TherapyDtoRetriever(tableInfoFactory);
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

    public DtoRetriever<TherapyDTO> getTherapyEntityetriever() {
        return therapyDtoRetriever;
    }
}