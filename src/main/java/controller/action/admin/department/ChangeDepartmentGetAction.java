package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import service.DepartmentService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeDepartmentGetAction implements Action {

    private ServiceFactory serviceFactory;

    public ChangeDepartmentGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int offset = Actions.paginateRequset(request);
        HttpSession session = request.getSession();

        DepartmentService service = serviceFactory.getDepartmentService();
        session.setAttribute("totalSize", service.getSize());
        session.setAttribute("departments", service.getAll(offset, Actions.PAGE_SIZE));

        if (session.getAttribute("submittedChangeDepartment") == null) {
            DepartmentActions.removeMsgAttributes(session);
        }

        session.removeAttribute("submittedChangeDepartment");
        String departmentIdParameter = request.getParameter("departmentId");
        if (departmentIdParameter != null) {
            String name = service.get(Long.parseLong(departmentIdParameter))
                    .orElseThrow(IllegalStateException::new)
                    .getName();
            session.setAttribute("departmentName", name);
        }

        return WebResources.get("admin.department.change");
    }
}
