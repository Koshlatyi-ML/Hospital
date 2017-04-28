package controller.action.admin.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.DepartmentDTO;
import domain.DoctorDTO;
import service.DepartmentService;
import service.DoctorService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeDoctorGetAction implements Action {

    private ServiceFactory serviceFactory;

    public ChangeDoctorGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");

        if (departmentId == null) {
            chooseDepartment(request);
            return WebResources.get("admin.doctor.change");
        }

        chooseDoctor(request, Long.parseLong(departmentId));
        return WebResources.get("admin.doctor.change");
    }

    private void chooseDepartment(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int offset = Actions.paginateRequset(request);

        DepartmentService service = serviceFactory.getDepartmentService();


        session.setAttribute("totalDepartmentSize", service.getSize() + 1);

        List<DepartmentDTO> allDepartments = service.getAll(offset, Actions.PAGE_SIZE);
        if (allDepartments.size() < Actions.PAGE_SIZE) {
            allDepartments.add(new DepartmentDTO.Builder().setId(-1).setName("Retired").build());
        }

        session.setAttribute("departments", allDepartments);
    }

    private void chooseDoctor(HttpServletRequest request, long departmentId) {
        HttpSession session = request.getSession();
        int offset = Actions.paginateRequset(request);

        DepartmentService departmentService = serviceFactory.getDepartmentService();
        session.setAttribute("departments",
                departmentService.getAll(0, (int) departmentService.getSize()));

        DoctorService doctorService = serviceFactory.getDoctorService();

        if (departmentId == -1) {
            session.setAttribute("doctors",
                    doctorService.getWithoutDepartmentId(offset, Actions.PAGE_SIZE));
            return;
        }

        session.setAttribute("doctors",
                doctorService.getByDepartmentId(departmentId, offset, Actions.PAGE_SIZE));
    }

}
