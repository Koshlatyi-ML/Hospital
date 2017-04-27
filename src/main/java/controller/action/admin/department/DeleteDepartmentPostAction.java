package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.DepartmentService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDepartmentPostAction implements Action {

    private ServiceFactory serviceFactory;

    public DeleteDepartmentPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");
        DepartmentService service = serviceFactory.getDepartmentService();
        service.delete(Integer.parseInt(departmentId));
        Actions.redirectToPage(response, WebPaths.get("admin.department.change"));
        return null;
    }
}
