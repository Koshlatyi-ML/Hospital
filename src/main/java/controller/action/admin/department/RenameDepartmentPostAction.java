package controller.action.admin.department;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import domain.DepartmentDTO;
import service.DepartmentService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class RenameDepartmentPostAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        DepartmentActions.removeMsgAttributes(session);

        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
        String departmentName = request.getParameter("department-name");
        if (!Validations.isValidDepartmentName(departmentName)) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.change.rename"));
            return null;
        }

        session.setAttribute("submittedRenameDepartment", "yes");
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/admin/department",
                Actions.parseLocaleAttribute(request.getSession().getAttribute("language")));

        if (service.getByName(departmentName).isPresent()) {
            session.setAttribute("usedDepartmentNameMsg", bundle.getString("usedName"));
            Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.change.rename"));
            return null;
        }

        long departmentId = Long.parseLong(request.getParameter("departmentId"));
        service.update(
                new DepartmentDTO.Builder()
                        .setId(departmentId)
                        .setName(departmentName)
                        .build());
        Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.change"));
        return null;
    }
}
