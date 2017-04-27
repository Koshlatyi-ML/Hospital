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

    private ServiceFactory serviceFactory;

    public PatientMainpageGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int offset = Actions.paginateRequset(request);
        HttpSession session = request.getSession();

        String departmentIdParameter = request.getParameter("departmentId");
        if (departmentIdParameter == null) {
            DepartmentService service = serviceFactory.getDepartmentService();
            session.setAttribute("totalSize", service.getSize());
            session.setAttribute("departments", service.getAll(offset, Actions.PAGE_SIZE));
            return getPatientMainpage(request);
        }

        DoctorService service = serviceFactory.getDoctorService();
        long departmentId = Long.parseLong(departmentIdParameter);
        session.setAttribute("totalSize", service.getByDepartmentIdSize(departmentId));
        session.setAttribute("doctors",
                service.getByDepartmentId(departmentId, offset, Actions.PAGE_SIZE));
        return getPatientMainpage(request);
    }

    private String getPatientMainpage(HttpServletRequest request) {
        setStateMsg(request);
        return WebResources.get("patient.main");
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
                String fromBundle = Actions.getFromBundle(request, basename, "state.discharged");
                session.setAttribute("stateMsg", fromBundle + "\"" + user.getDiagnosis() + "\"" + ".");
                break;
            case "APPLIED":
                session.setAttribute("stateMsg",
                        Actions.getFromBundle(request, basename, "state.applied"));
                break;
            case "TREATED":
                session.setAttribute("stateMsg",
                        Actions.getFromBundle(request, basename, "state.applied"));
                break;
        }
    }
}
