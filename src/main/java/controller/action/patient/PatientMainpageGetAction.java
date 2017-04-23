package controller.action.patient;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.PatientDTO;
import service.DepartmentService;
import service.DoctorService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PatientMainpageGetAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * Actions.PAGE_SIZE;

        HttpSession session = request.getSession();
        session.setAttribute("page", page);

        String departmentIdParameter = request.getParameter("departmentId");
        if (departmentIdParameter == null) {
            DepartmentService service = ServiceFactory.getInstance().getDepartmentService();
            session.setAttribute("totalSize", service.getSize());
            session.setAttribute("departments", service.getAll(offset, Actions.PAGE_SIZE));
        } else {
            DoctorService service = ServiceFactory.getInstance().getDoctorService();
            long departmentId = Long.parseLong(departmentIdParameter);
            session.setAttribute("totalSize", service.getByDepartmentIdSize(departmentId));
            session.setAttribute("doctors",
                    service.getByDepartmentId(departmentId, offset, Actions.PAGE_SIZE));
        }

        setStateMsg(request);
        return WebResources.webResources.get("patient.main");
    }

    private void setStateMsg(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PatientDTO user = (PatientDTO) session.getAttribute("user");
        final String basename = "i18n/patient";
        switch (user.getState()) {
            case "REGISTERED":
                session.setAttribute("stateMsg",
                        Actions.getFromBundle(request, basename, "state.registered"));
                break;
            case "DISCHARGED":
                session.setAttribute("stateMsg", Actions.getFromBundle(request,
                        basename + user.getDiagnosis() + ".", "state.discharged"));
                break;
            case "APPLIED":
                session.setAttribute("stateMsg",
                        Actions.getFromBundle(request, basename, "state.applied"));
                break;
            case "TREATED":
                session.setAttribute("stateMsg",
                        Actions.getFromBundle(request, basename, "state.treated"));
                break;
        }
    }
}
