package controller.action.registration;

import controller.action.Action;
import controller.action.Actions;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.dto.PatientRegistrationDTO;
import service.dto.StuffRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationPostAction implements Action {

    private ServiceFactory serviceFactory;

    public RegistrationPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Registrations.removeRegistrationErrorAttributes(session);

        PatientRegistrationDTO dto = Registrations.fetchPatientRegistrationDTO(request);
        if (dto == null) {
            session.setAttribute("failedRegistration", "yes");
            Actions.redirectToPage(response, WebPaths.get("registration"));
            return null;
        }

        session.setAttribute("logined", "yes");
        session.setAttribute("role", "Patient");
        session.setAttribute("user", serviceFactory.getPatientService().register(dto));
        Actions.redirectToPage(response, WebPaths.get("patient.main"));
        return null;
    }
}
