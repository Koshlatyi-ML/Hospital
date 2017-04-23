package controller.action.admin.medic;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddMedicGetAction implements Action {

    private ServiceFactory serviceFactory;

    public AddMedicGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MedicRegistrations.prepareRegistrationGetAction(request);
        StuffRegistrations.loadDepartmentsAttribute(request);
        return WebResources.webResources.get("admin.doctor.add");
    }
}
