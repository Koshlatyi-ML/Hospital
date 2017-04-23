package controller.action.medic;

import controller.action.Action;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitMedicHomepageAction implements Action {

    private ServiceFactory serviceFactory;

    public VisitMedicHomepageAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.webResources.get("medic.main");
    }
}
