package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.DepartmentService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDepartmentPostAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");
        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
        service.delete(Integer.parseInt(departmentId));
        Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.change"));
        return null;
    }
}
