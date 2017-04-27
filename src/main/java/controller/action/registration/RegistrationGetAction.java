package controller.action.registration;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationGetAction implements Action {

    private ServiceFactory serviceFactory;

    public RegistrationGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StuffRegistrations.loadDepartmentsAttribute(request);
        PatientRegistrations.prepareRegistrationGetAction(request);
        return WebResources.get("registration");
    }
}
