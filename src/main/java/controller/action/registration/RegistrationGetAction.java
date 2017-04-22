package controller.action.registration;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StuffRegistrations.loadDepartmentsAttribute(request);
        PatientRegistrations.prepareRegistrationGetAction(request);
        return WebResources.webResources.get("registration");
    }
}
