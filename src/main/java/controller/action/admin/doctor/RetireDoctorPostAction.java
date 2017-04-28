package controller.action.admin.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import domain.DoctorDTO;
import service.DoctorService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RetireDoctorPostAction implements Action {

    private ServiceFactory serviceFactory;

    public RetireDoctorPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        DoctorService service = serviceFactory.getDoctorService();
        DoctorDTO doctorDTO = service.get(doctorId).orElseThrow(IllegalArgumentException::new);
        doctorDTO.setDepartmentId(0);
        service.update(doctorDTO);
        Actions.redirectToPage(response, WebPaths.get("admin.doctor.change"));
        return null;
    }
}
