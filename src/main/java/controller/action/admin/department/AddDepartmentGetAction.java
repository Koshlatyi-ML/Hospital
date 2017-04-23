package controller.action.admin.department;

import controller.action.Action;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddDepartmentGetAction implements Action {

    private ServiceFactory serviceFactory;

    public AddDepartmentGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("submittedAddDepartment") == null) {
            DepartmentActions.removeMsgAttributes(session);
        }

        session.removeAttribute("submittedAddDepartment");
        return WebResources.webResources.get("admin.department.add");
    }
}
