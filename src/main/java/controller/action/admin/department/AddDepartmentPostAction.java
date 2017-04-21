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

public class AddDepartmentPostAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        DepartmentActions.removeMsgAttributes(session);

        DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
        String departmentName = request.getParameter("department-name");
        if (!Validations.isValidDepartmentName(departmentName)) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.add"));
            return null;
        }

        session.setAttribute("submittedAddDepartment", "yes");
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/admin/add-department",
                Actions.parseLocaleAttribute(request.getSession().getAttribute("language")));

        if (service.getByName(departmentName).isPresent()) {
            session.setAttribute("usedDepartmentNameMsg", bundle.getString("usedName"));
            Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.add"));
            return null;
        }

        service.create(new DepartmentDTO.Builder().setName(departmentName).build());
        session.setAttribute("successfulAddDepartment", bundle.getString("success"));
        Actions.redirectToPage(response, WebPaths.webPaths.get("admin.department.add"));
        return null;
    }
}
