package controller.action.medic;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.TherapyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MedicsTherapyPostAction implements Action {

    private ServiceFactory serviceFactory;

    public MedicsTherapyPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long therapyId = Long.parseLong(request.getParameter("therapyId"));
        TherapyService service = serviceFactory.getTherapyService();
        service.performTherapy(therapyId);
        Actions.redirectToPage(response, WebPaths.webPaths.get("medic.main"));
        return null;
    }
}
