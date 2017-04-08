package dao;

public interface DaoFactory {
    DepartmentDAO getDepartmentDao();
    DoctorDAO getDoctorDao();
    MedicDAO getMedicDao();
    PatientDAO getPatientDao();
    TherapyDAO getTherapyDao();
    CredentialsDAO getCredentialsDao();
}
