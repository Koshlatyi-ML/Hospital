package controller.action.admin;

import controller.action.registration.Registrations;
import dao.DaoManager;
import dao.DepartmentDAO;
import domain.DepartmentDTO;
import service.DepartmentService;
import service.ServiceFactory;
import service.dto.PatientRegistrationDTO;
import service.dto.StuffRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class StuffRegistrations {

    private StuffRegistrations() {
    }

    public static void loadDepartmentsAttribute(HttpServletRequest request) {
        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();

        List<DepartmentDTO> allDepartments = service.getAll(0, (int) service.getSize());
        request.getSession().setAttribute("departments", allDepartments);
    }

    public static StuffRegistrationDTO fetchStuffRegistrationDTO(HttpServletRequest request) {
        PatientRegistrationDTO dto = Registrations.fetchPatientRegistrationDTO(request);

        if (dto == null) {
            return null;
        }

        return new StuffRegistrationDTO.Builder()
                .setName(dto.getName())
                .setSurname(dto.getSurname())
                .setLogin(dto.getLogin())
                .setPassword(dto.getPassword())
                .setDepartmentId(Integer.parseInt(request.getParameter("departmentId")))
                .build();
    }
}
