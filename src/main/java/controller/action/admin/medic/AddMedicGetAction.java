package controller.action.admin.medic;

import controller.action.Action;
import controller.action.admin.StuffRegistrations;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddMedicGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MedicRegistrations.prepareRegistrationGetAction(request);
        StuffRegistrations.loadDepartmentsAttribute(request);
        return WebResources.webResources.get("admin.doctor.add");
    }
}
