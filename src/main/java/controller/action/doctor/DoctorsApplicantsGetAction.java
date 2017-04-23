package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.DoctorDTO;
import domain.TherapyDTO;
import service.PatientService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoctorsApplicantsGetAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorsApplicantsGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("failedTherapySubmitting") == null) {
            session.removeAttribute("invalidInputMsg");
        }
        session.removeAttribute("failedTherapySubmitting");

        PatientService service = serviceFactory.getPatientService();
        int offset = Actions.paginateRequset(request);

        long userId = ((DoctorDTO) session.getAttribute("user")).getId();

        String patientIdParameter = request.getParameter("patientId");
        if (patientIdParameter == null) {
            session.setAttribute("totalSize", service.getAppliedPatientsOfDoctorSize(userId));
            session.setAttribute("patients",
                    service.getAppliedPatientsOfDoctor(userId, offset, Actions.PAGE_SIZE));
            return WebResources.webResources.get("doctor.applicants");
        }

        if (session.getAttribute("therapyTypes") == null) {
            session.setAttribute("therapyTypes", TherapyDTO.Type.values());
        }

        return WebResources.webResources.get("doctor.applicants");
    }
}
