package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.DoctorDTO;
import domain.TherapyDTO;
import service.MedicService;
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

        DoctorDTO user = (DoctorDTO) session.getAttribute("user");
        int offset = Actions.paginateRequset(request);

        long userId = user.getId();
        String patientIdParameter = request.getParameter("patientId");
        if (patientIdParameter == null) {
            PatientService patientService = serviceFactory.getPatientService();
            session.setAttribute("totalSize", patientService.getAppliedPatientsOfDoctorSize(userId));
            session.setAttribute("patients",
                    patientService.getAppliedPatientsOfDoctor(userId, offset, Actions.PAGE_SIZE));
            return WebResources.webResources.get("doctor.applicants");
        }

        MedicService medicService = serviceFactory.getMedicService();
        session.setAttribute("medics",
                medicService.getByDepartmentId(user.getDepartmentId(), offset, Actions.PAGE_SIZE));

        if (session.getAttribute("therapyTypes") == null) {
            session.setAttribute("therapyTypes", TherapyDTO.Type.values());
        }

        return WebResources.webResources.get("doctor.applicants");
    }
}
