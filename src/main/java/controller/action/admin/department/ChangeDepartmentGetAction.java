package controller.action.admin.department;

import controller.action.Action;
import controller.constants.WebResources;
import service.DepartmentService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeDepartmentGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        request.getSession().setAttribute("page", page);
        request.getSession().setAttribute("departments", service.getAll());
        return WebResources.webResources.get("admin.department.change");
    }
}
