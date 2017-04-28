package controller.action.admin.medic;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import domain.DoctorDTO;
import domain.MedicDTO;
import service.DoctorService;
import service.MedicService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoveMedicPostAction implements Action {

    private ServiceFactory serviceFactory;

    public MoveMedicPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long medicId = Long.parseLong(request.getParameter("medicId"));
        MedicService service = serviceFactory.getMedicService();
        MedicDTO medicDTO = service.get(medicId).orElseThrow(IllegalArgumentException::new);
        medicDTO.setDepartmentId(Long.parseLong(request.getParameter("departmentId")));
        service.update(medicDTO);
        Actions.redirectToPage(response, WebPaths.get("admin.medic.change"));
        return null;
    }
}
