package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.ServiceFactory;
import service.TherapyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorsTherapyRedirectPostAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorsTherapyRedirectPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long medicId = Long.parseLong(request.getParameter("medic"));
        long therapyId = Long.parseLong(request.getParameter("therapyId"));
        TherapyService therapyService = serviceFactory.getTherapyService();
        therapyService.changePerformer(therapyId, medicId);
        Actions.redirectToPage(response, WebPaths.get("doctor.therapies"));
        return null;
    }
}
