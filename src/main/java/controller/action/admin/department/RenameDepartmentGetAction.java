package controller.action.admin.department;

import controller.action.Action;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RenameDepartmentGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("submittedRenameDepartment") == null) {
            DepartmentActions.removeMsgAttributes(session);
        }

        session.removeAttribute("submittedRenameDepartment");
        return WebResources.webResources.get("admin.department.change.rename");
    }
}
