package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.DepartmentService;
import service.DoctorService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteDepartmentPostAction implements Action {

    private ServiceFactory serviceFactory;

    public DeleteDepartmentPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String departmentId = request.getParameter("departmentId");
        DepartmentService departmentService = serviceFactory.getDepartmentService();
        DoctorService doctorService = serviceFactory.getDoctorService();

        HttpSession session = request.getSession();
        DepartmentActions.removeMsgAttributes(session);
        session.setAttribute("submittedChangeDepartment", "yes");

        int id = Integer.parseInt(departmentId);
        if (doctorService.getByDepartmentIdSize(id) > 0) {
            session.setAttribute("notEmptyDepartmentMsg", Actions.getFromBundle(request,
                    "i18n/admin/department", "notEmpty"));
            Actions.redirectToPage(response, WebPaths.get("admin.department.change"));
            return null;
        }

        departmentService.delete(id);
        Actions.redirectToPage(response, WebPaths.get("admin.department.change"));
        return null;
    }
}
