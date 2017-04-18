package controller.action.registration;

import controller.action.Action;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MedicRegistrationGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Registrations.prepareRegistrationGetAction(request);
        StuffRegistrations.loadDepartmentsAttribute(request);
        return WebResources.webResources.get("registration.medic");
    }
}
