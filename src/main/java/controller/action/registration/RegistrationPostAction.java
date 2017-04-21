package controller.action.registration;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.dto.PatientRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationPostAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Registrations.removeRegistrationErrorAttributes(session);

        PatientRegistrationDTO dto = Registrations.fetchPatientRegistrationDTO(request);
        if (dto == null) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("registration"));
            return null;
        }

        session.setAttribute("logined", "yes");
        session.setAttribute("role", "Patient");
        session.setAttribute("user", ServiceFactory.getInstance().getPatientService().register(dto));
        Actions.redirectToPage(response, WebPaths.webPaths.get("patient.main"));

        return null;
    }
}
