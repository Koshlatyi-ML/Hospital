package controller.action.admin.doctor;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;
import service.ServiceFactory;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDoctorGetAction implements Action {

    private ServiceFactory serviceFactory;

    public AddDoctorGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DoctorRegistrations.prepareRegistrationGetAction(request);
        StuffRegistrations.loadDepartmentsAttribute(request);
        return WebResources.webResources.get("admin.doctor.add");
    }
}
