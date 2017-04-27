package controller.action.admin.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.action.registration.Registrations;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.dto.StuffRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddDoctorPostAction implements Action {

    private ServiceFactory serviceFactory;

    public AddDoctorPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Registrations.removeRegistrationErrorAttributes(session);
        session.setAttribute("submittedAddDoctor", "yes");

        StuffRegistrationDTO dto = StuffRegistrations.fetchStuffRegistrationDTO(request);
        if (dto == null) {
            Actions.redirectToPage(response, WebPaths.get("admin.doctor.add"));
            return null;
        }

        String successMsg = Actions.getFromBundle(request, "i18n/messages", "success");
        session.setAttribute("successfulAddDoctor", successMsg);
        serviceFactory.getDoctorService().register(dto);
        Actions.redirectToPage(response, WebPaths.get("admin.doctor.add"));
        return null;
    }
}
