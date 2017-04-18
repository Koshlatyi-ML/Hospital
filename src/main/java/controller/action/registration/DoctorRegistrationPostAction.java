package controller.action.registration;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.dto.StuffRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoctorRegistrationPostAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Registrations.removeRegistrationErrorAttributes(session);

        StuffRegistrationDTO dto = StuffRegistrations.fetchStuffRegistrationDTO(request);
        if (dto == null) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("registration.doctor"));
            return null;
        }

        session.setAttribute("logined", "yes");
        session.setAttribute("role", "Doctor");
        session.setAttribute("user", ServiceFactory.getInstance().getDoctorService().register(dto));
        Actions.redirectToPage(response, WebPaths.webPaths.get("doctor.main"));

        return null;
}
}
