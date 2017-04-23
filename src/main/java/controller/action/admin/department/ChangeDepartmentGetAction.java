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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * Actions.PAGE_SIZE;

        HttpSession session = request.getSession();
        session.setAttribute("page", page);
        session.setAttribute("totalSize", service.getSize());
        session.setAttribute("departments",
                service.getAll(offset, Actions.PAGE_SIZE));

        if (session.getAttribute("submittedRenameDepartment") == null) {
            DepartmentActions.removeMsgAttributes(session);
        }

        session.removeAttribute("submittedRenameDepartment");
        String departmentIdParameter = request.getParameter("departmentId");
        if (departmentIdParameter != null) {
            String name = service.get(Long.parseLong(departmentIdParameter))
                    .orElseThrow(IllegalStateException::new)
                    .getName();
            session.setAttribute("departmentName", name);
        }

        return WebResources.webResources.get("admin.department.change");
    }
}
