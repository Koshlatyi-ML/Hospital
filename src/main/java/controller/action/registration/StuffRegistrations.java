package controller.action.registration;

import dao.DaoManager;
import dao.DepartmentDAO;
import domain.DepartmentDTO;
import service.dto.PatientRegistrationDTO;
import service.dto.StuffRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

class StuffRegistrations {

    private StuffRegistrations() {
    }

    static void loadDepartmentsAttribute(HttpServletRequest request) {
        DepartmentDAO departmentDao = DaoManager.getInstance().getDepartmentDao();
        List<DepartmentDTO> allDepartments = departmentDao.findAll();
        request.getSession().setAttribute("departments", allDepartments);
    }

    static StuffRegistrationDTO fetchStuffRegistrationDTO(HttpServletRequest request) {
        PatientRegistrationDTO dto = Registrations.fetchPatientRegistrationDTO(request);

        if (dto == null) {
            return null;
        }

        return new StuffRegistrationDTO.Builder()
                .setName(dto.getName())
                .setSurname(dto.getSurname())
                .setLogin(dto.getLogin())
                .setPassword(dto.getPassword())
                .setDepartmentId(Integer.parseInt(request.getParameter("department")))
                .build();
    }
}
