package controller.action.admin.doctor;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDoctorGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DoctorRegistrations.prepareRegistrationGetAction(request);
        StuffRegistrations.loadDepartmentsAttribute(request);
        return WebResources.webResources.get("admin.doctor.add");
    }
}
