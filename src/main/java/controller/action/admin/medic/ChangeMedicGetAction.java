package controller.action.admin.medic;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.DepartmentDTO;
import service.DepartmentService;
import service.DoctorService;
import service.MedicService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeMedicGetAction implements Action {

    private ServiceFactory serviceFactory;

    public ChangeMedicGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");

        if (departmentId == null) {
            chooseDepartment(request);
            return WebResources.get("admin.medic.change");
        }

        chooseMedic(request, Long.parseLong(departmentId));
        return WebResources.get("admin.medic.change");
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

    private void chooseMedic(HttpServletRequest request, long departmentId) {
        HttpSession session = request.getSession();
        int offset = Actions.paginateRequset(request);

        DepartmentService departmentService = serviceFactory.getDepartmentService();
        session.setAttribute("departments",
                departmentService.getAll(0, (int) departmentService.getSize()));

        MedicService medicService = serviceFactory.getMedicService();

        if (departmentId == -1) {
            session.setAttribute("medics",
                    medicService.getWithoutDepartmentId(offset, Actions.PAGE_SIZE));
            session.setAttribute("totalMedicSize", medicService.getWithoutDepartmentIdSize());
            return;
        }

        session.setAttribute("medics",
                medicService.getByDepartmentId(departmentId, offset, Actions.PAGE_SIZE));
        session.setAttribute("totalMedicSize", medicService.getByDepartmentIdSize(departmentId  ));
    }
}
