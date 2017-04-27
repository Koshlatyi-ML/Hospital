package controller.action.admin.medic;

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

public class AddMedicPostAction implements Action {

    private ServiceFactory serviceFactory;

    public AddMedicPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Registrations.removeRegistrationErrorAttributes(session);
        session.setAttribute("submittedAddMedic", "yes");

        StuffRegistrationDTO dto = StuffRegistrations.fetchStuffRegistrationDTO(request);
        if (dto == null) {
            Actions.redirectToPage(response, WebPaths.get("admin.medic.add"));
            return null;
        }

        String successMsg = Actions.getFromBundle(request, "i18n/messages", "success");
        session.setAttribute("successfulAddMedic", successMsg);
        serviceFactory.getMedicService().register(dto);
        Actions.redirectToPage(response, WebPaths.get("admin.medic.add"));
        return null;
    }
}
