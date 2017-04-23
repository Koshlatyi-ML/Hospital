package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
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
        int offset = (page - 1) * Actions.PAGE_SIZE;

        request.getSession().setAttribute("page", page);
        request.getSession().setAttribute("totalSize", service.getSize());
        request.getSession().setAttribute("departments",
                service.getAll(offset, Actions.PAGE_SIZE));
        return WebResources.webResources.get("admin.department.change");
    }
}
